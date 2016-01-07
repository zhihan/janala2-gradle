#!/usr/bin/env bash
#
# Run concolic analyzer up to a given iterations.

function getcwd() {
    pushd `dirname $0` > /dev/null
    readonly SCRIPT_DIR=$(pwd)
    popd > /dev/null
}


function set_constants() {
    getcwd
    readonly ROOT=$(dirname ${SCRIPT_DIR})
    readonly CATG_TMP_DIR="catg_tmp"
}


function concolic() {
    java -Xmx4096M -Xms2048M \
         -cp "${ROOT}/lib/catg-dev.jar:${ROOT}/lib/asm-all-5.0.4.jar:${ROOT}/lib/automaton-1.11-8.jar:${ROOT}/build/classes/integration" \
         -Djanala.loggerClass=janala.logger.DirectConcolicExecution \
         -Djanala.conf=${ROOT}/catg.conf \
         -javaagent:"${ROOT}/lib/catg-dev.jar" \
         -ea ${CLASS_NAME}
}

function is_real_input() {
    if [[ -f "isRealInput" ]]; then
        result=$(cat isRealInput | sed -e 's/\n//')
        echo ${result}
    else
        echo "true"
    fi
}

function loop() {
    if [[ -d "${CATG_TMP_DIR}" ]]; then
        rm -rf "${CATG_TMP_DIR}"
    fi
    mkdir ${CATG_TMP_DIR}
    cd ${CATG_TMP_DIR}
    
    declare -i iter=1
    while (($iter <= $MAX_ITERATIONS)); do
        local data=$(is_real_input)

        if [[ "${data}" != "false" ]]; then
            cp inputs inputs.${iter}
        fi 
        cp history history.old

        concolic || exit 1
        iter+=1

        if [[ -f "history" ||  -f "backtrackFlag" ]]; then
            echo "continue"
        elif (( $iter == $MAX_ITERATIONS )); then
            echo "${CLASS_NAME} (${MAX_ITERATIONS}) passed" >> ../test.log
            return
        else
            echo "${CLASS_NAME} ($MAX_ITERATIONS}) passed at $iter" >> ../test.log
            return
        fi  
    done
    echo "******** ${CLASS_NAME} failed!!!" >> ../test.log
}

set_constants

readonly MAX_ITERATIONS=$1
readonly CLASS_NAME=$2

loop
