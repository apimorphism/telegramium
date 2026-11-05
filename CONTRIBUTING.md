# Contributing

I'd love to have more testing and more example bots. Ideas and PRs on
`telegramium-high` - the high level interface for the bot API - are also
highly encouraged. Any PRs besides `telegramium-core` are welcome.

## telegramium-core is generated - don't hand-edit it

`telegramium-core` is generated from the official
[Telegram Bot API docs](https://core.telegram.org/bots/api) by
[apimorphism/iozhik](https://github.com/apimorphism/iozhik). Do not open PRs
with hand-written changes to `telegramium-core`; they'll be overwritten the
next time it's regenerated. Instead:

- If something in `telegramium-core` looks wrong (a missing field, a wrong
  type, bad formatting, etc.), please open an issue here describing it.
- If you want to fix it yourself, the fix belongs in `apimorphism/iozhik`
  (the HTML scraper, the generator config, or the DSL -> Scala codegen), not
  here. See that repo's CONTRIBUTING.md for the regeneration flow, then come
  back here with the regenerated `telegramium-core` as part of your PR.

The test-scope generators are generated too, into
`telegramium-core/src/test/scala-generated` (wiped on every regen, like
`src/main/scala`, and formatted by iozhik, so `fmtAll` leaves it alone). They
feed `CodecRoundTripSpec`, which checks that every type with both an encoder and
a decoder survives encode → decode, and telegramium-high's `WireSpec`, which
checks the wire request of every Method, both sent through `BotApi`
(`BotApiWireSpec`) and returned as a webhook reply (`WebhookBotWireSpec`).
A restriction on a generator belongs in iozhik's `custom-gens` config, with a
comment explaining it.

## Code style

Before you submit a PR, ensure that you've formatted your code by running:

```
sbt fmtAll
```

(Optional: `nix develop` / `direnv allow` loads a dev shell with this as the
`core-fmt` alias. Not required; the raw command above works the same without
Nix.)

## Live test bot (maintainers)

The live test talks to production Telegram as a dedicated bot with no real
users, posting to a private channel. It reads `TELEGRAM_LIVE_BOT_TOKEN` and
`TELEGRAM_LIVE_CHAT_ID`, from `.env` locally and from GitHub secrets in CI. To
create the bot and channel, or re-create them, run:

```
scripts/live-bot-wizard.sh
```

It walks you through @BotFather and the channel setup, then writes both values
to `.env` (gitignored) and to the secrets on `apimorphism/telegramium`.

The live test is `TelegramLiveSpec` in telegramium-high. Its tests are tagged
`LiveTest`, which plain `sbt test` skips. To run them, and only them, against
Telegram:

```
set -a; . ./.env; set +a
sbt liveTest
```

Each test posts a message to the channel. In CI, `.github/workflows/live.yml`
runs them nightly, on manual trigger, and on PRs from this repo that touch
telegramium-core's main code, telegramium-high or the workflow itself (fork PRs
get no secrets, so it's skipped there). A failing nightly run opens a "Live
test failing" issue, or comments on the open one; the next green nightly run
closes it.

## Recorded fixtures (maintainers)

`telegramium-core/src/test/resources/fixtures` holds real JSON that Telegram
sent the live test bot. It has `updates/` and `responses/` (of `getMe`,
`getChat`, ...) and is scrubbed of personal data. `FixturesSpec` checks that
every fixture decodes and gives the same value after decode → encode → decode.
To refresh the fixtures, for example after a Bot API release:

1. Record. Leave this running while you produce updates by hand: post, edit
   and react in the channel, and message the bot privately (text, photos,
   stickers, voice, polls, locations, contacts, forwards, replies, edits,
   reactions, blocking and unblocking it, ...):

   ```
   set -a; . ./.env; set +a
   sbt "telegramium-examples3/runMain telegramium.bots.examples.CaptureBot"
   ```

   `CaptureBot` saves each raw update, and the responses for each chat, user
   and file it mentions, to `.fixtures-raw/` (gitignored). Stop it with Ctrl-C.
   A later session adds to what's already there.
2. Scrub. This replaces `fixtures/updates` and `fixtures/responses` with a
   scrubbed copy of everything in `.fixtures-raw/`:

   ```
   sbt "telegramium-examples3/runMain telegramium.bots.examples.FixtureScrubber"
   ```

   IDs are remapped consistently, keeping IDs above 2³¹ above it. File IDs
   become fakes of the same shape, and locations move to a fixed place. Every
   other string has its letters and digits replaced, unless its key is listed in
   `FixtureScrubber`'s `keptStrings`. If `FixturesSpec` then fails on a
   decoder's discriminator, add that key there.
3. Check with `sbt "telegramium-core3/testOnly telegramium.bots.FixturesSpec"`.
   Then read the diff for anything personal before you commit it.

## Releasing (maintainers)

The version comes from the git tag on `HEAD` (via sbt-dynver, see `build.sbt`)
- no separate version to bump by hand. To cut a release:

1. Tag the commit: `core-tag X.Y.Z` (dev shell; checks the tree is clean and
   the tag doesn't already exist) or plain `git tag -a X.Y.Z -m "Release X.Y.Z"`.
2. Push the tag: `git push origin X.Y.Z`.
3. Build and publish from that tag (`sbt publishSigned`, then release via
   Central Portal).
4. Create the GitHub Release against that already-existing tag, rather than
   letting the Release UI create a new one - this keeps the published version
   and the release tag identical by construction.
