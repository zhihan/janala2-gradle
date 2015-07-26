#!/bin/bash

# Set up the environments to run 
echo "$ROOT"

CLASSPATH="${ROOT}/lib/automaton.jar:\
${ROOT}/lib/choco-solver-2.1.4.jar:\
${ROOT}/lib/emma.jar:\
${ROOT}/lib/trove-3.0.3.jar:\
${ROOT}/lib/asm-all-5.0.4.jar:\
${ROOT}/lib/catg-dev.jar"



