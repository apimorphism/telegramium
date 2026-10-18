{
  description = "A Nix-flake-based Scala development environment";

  inputs.nixpkgs.url = "https://flakehub.com/f/NixOS/nixpkgs/0.1";

  outputs =
    { self, ... }@inputs:

    let
      javaVersion = 21; # Change this value to update the whole stack

      supportedSystems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
      ];
      forEachSupportedSystem =
        f:
        inputs.nixpkgs.lib.genAttrs supportedSystems (
          system:
          f {
            inherit system;
            pkgs = import inputs.nixpkgs {
              inherit system;
              overlays = [ inputs.self.overlays.default ];
            };
          }
        );
    in
    {
      overlays.default =
        final: prev:
        let
          jdk = prev."jdk${toString javaVersion}";
        in
        {
          sbt = prev.sbt.override { jre = jdk; };
          scala = prev.scala_3.override { jre = jdk; };
        };

      devShells = forEachSupportedSystem (
        { pkgs, system }:
        {
          # core-fmt implements the code-style step documented in
          # CONTRIBUTING.md. The core-regeneration commands themselves live
          # in apimorphism/iozhik's dev shell.
          default = pkgs.mkShellNoCC {
            packages = with pkgs; [
              scala
              sbt
              coursier
              gh
            ];

            shellHook = ''
              core-fmt() {
                sbt fmtAll
              }

              # Tags HEAD for a release. Version comes from this tag via sbt-dynver (see
              # build.sbt), so a release is just: tag, push the tag, build & publish from it,
              # then point a GitHub Release at the already-existing tag.
              core-tag() {
                local version="$1"
                if [[ -z "$version" ]]; then
                  echo "usage: core-tag <X.Y.Z>" >&2
                  return 1
                fi
                if ! [[ "$version" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
                  echo "core-tag: '$version' doesn't look like X.Y.Z" >&2
                  return 1
                fi
                if [[ -n "$(git status --porcelain)" ]]; then
                  echo "core-tag: working tree is dirty, commit or stash first" >&2
                  return 1
                fi
                if git rev-parse "$version" >/dev/null 2>&1; then
                  echo "core-tag: tag $version already exists" >&2
                  return 1
                fi
                echo "Latest tag: $(git describe --tags --abbrev=0 2>/dev/null || echo "none")"
                git tag -a "$version" -m "Release $version"
                echo "Tagged $(git rev-parse --short HEAD) as $version. Push it with:"
                echo "  git push origin $version"
              }

              export -f core-fmt core-tag

              echo "telegramium dev shell: core-fmt, core-tag"
            '';
          };
        }
      );
    };
}
