#!/bin/bash

# Check if `byaccj` is installed
if ! [ -x "$(command -v byaccj)" ]; then
  echo 'Error: byaccj is not installed.' >&2
  exit 1
fi

# Get the root of the project
ROOT=$(git rev-parse --show-toplevel)

# Iterate over all `.y` files in `src/main/jflex` folder
find "${ROOT}/src/main/jflex" -name "*.y" | while read -r file; do
    # Get the folder name of the file, and replace `jflex` with `java`
    folder=$(dirname "${file}" | sed 's/jflex/java/')

    # Determine the java package based on the folder
    package=$(echo "${folder}" | sed 's|'"${ROOT}"'/src/main/java/||' | sed 's|/|.|g')

    cd "${folder}" || exit 1

    # Generate the parser
    byaccj -J                           \
        -Jpackage="${package}"          \
        -Jyylex=simple.yylex            \
        -Jyyerror=simple.yyerror        \
        "${file}"

    cd "${ROOT}" || exit 1
done
