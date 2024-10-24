#!/bin/bash

# Check if `dot` is installed 
if ! [ -x "$(command -v dot)" ]; then
  echo 'Error: dot is not installed.' >&2
  exit 1
fi 

# Get the root of the project 
ROOT=$(git rev-parse --show-toplevel)

# Iterate over all `.dot` files in `examples` folder
find "${ROOT}/examples" -name "*.dot" | while read -r file; do
    outfile="${file//.dot/.png}"

    echo "Generating $(basename "${outfile}") from $(basename "${file}")"

    # Generate the image from the dot file
    dot -Tpng -Gdpi=300 "${file}" -o "${outfile}"
done
