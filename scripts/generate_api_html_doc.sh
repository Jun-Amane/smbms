#!/bin/zsh

SCRIPT_DIR=${0:A:h}

find "$SCRIPT_DIR/../doc/api/OpenAPI YAML" -type f -name "*.yaml" -exec sh -c '
  DIR=$(dirname "{}")
  FILE=$(basename "{}" .yaml)
  swagger-codegen generate -i "{}" -l html -o "$DIR/../HTML/$FILE"
' \;


