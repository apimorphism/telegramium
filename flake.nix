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

              export -f core-fmt

              echo "telegramium dev shell: core-fmt"
            '';
          };
        }
      );
    };
}
