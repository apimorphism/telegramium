# Agent instructions

This repo is `apimorphism/telegramium`. Agents working here should push
branches and open PRs directly against it, not through a personal fork.

## Remotes and auth

- `origin` must point at `https://github.com/apimorphism/telegramium.git`.
  There should be no `fork` remote - if one exists, it's stale, remove it
  (`git remote remove fork`).
- Use the `GH_TOKEN_APIMORPHISM` token (scoped to the `apimorphism` org) for
  anything that writes to this repo on GitHub - pushing branches, creating
  PRs, commenting. Do not use a personal `GH_TOKEN` for these; it may not
  have write access to `apimorphism/*` at all, and even if it does, commits
  should be attributed through the org-scoped token.
- Create your own branch off `origin/master` (never commit to `master`
  directly), e.g.:
  ```
  git checkout -b <branch> origin/master
  ```

## Pushing and opening PRs

From the dev shell (see below for how to enter it):

```
core-push       # GH_TOKEN="$GH_TOKEN_APIMORPHISM" git push -u origin <current branch>
core-pr-draft   # GH_TOKEN="$GH_TOKEN_APIMORPHISM" gh pr create --repo apimorphism/telegramium --draft --fill
```

Equivalently, without the aliases:

```
GH_TOKEN="$GH_TOKEN_APIMORPHISM" git push -u origin HEAD
GH_TOKEN="$GH_TOKEN_APIMORPHISM" gh pr create --repo apimorphism/telegramium --draft --fill
```

Open PRs as drafts by default; let a human mark them ready for review.

## Dev shell

`direnv allow` (or `nix develop`) loads a shell with `sbt`/`scala`/`gh` and
the aliases above (`core-fmt`, `core-push`, `core-pr-draft`).

## Rules

- Never hand-edit `telegramium-core` - it's generated from
  `apimorphism/iozhik`. See [CONTRIBUTING.md](CONTRIBUTING.md) and that
  repo's CONTRIBUTING.md/AGENTS.md for the scrape -> regenerate flow (the
  `tgbot-scrape` / `tgbot-regen-core` / `tgbot-refresh` commands live in
  iozhik's dev shell, not this one).
- Run `core-fmt` and `sbt test` before opening a PR. A few integration specs
  (`LongPollBotISpec`, `WebhookBotISpec`, `BotApiSpec`) need Docker via
  Testcontainers; if Docker isn't available or a container is slow to start,
  those specs abort rather than the whole suite failing for a code reason.
