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

## Code style

Before you submit a PR, ensure that you've formatted your code by running:

```
sbt fmtAll
```

(Optional: `nix develop` / `direnv allow` loads a dev shell with this as the
`core-fmt` alias. Not required; the raw command above works the same without
Nix.)
