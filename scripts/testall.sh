#!/usr/bin/env bash
SCRIPT_DIR=`dirname $0`


function main() {
  declare -a all_tests=(\
"5 tests.test_q1_q4_min" \
"9 tests.test_q1_q4" \
"3 tests.test_q0_q4" \
"3 tests.BoolFlow" \
"2 tests.BoolFlow2" \
"3 tests.BoolTest" \
"5 tests.BoolTest2" \
"5 tests.BoolTest3" \
"3 tests.Array1" \
"3 tests.Array2" \
"3 tests.Array3" \
"2 tests.Array4" \
"3 tests.StringEquals" \
"4 tests.StringTest2" \
"4 tests.StringTest3" \
"4 tests.StringTest4" \
"3 tests.StringTest5" \
"4 tests.StringTest6" \
"4 tests.StringTest7" \
"4 tests.StringTest8" \
"4 tests.StringTest9" \
"6 tests.StringSubStringTest2" \
"7 tests.StringComplexTest1" \
"5 tests.StringComplexTest3" \
"3 tests.StringRegexTest1" \
"3 tests.StringRegexTest2" \
"4 tests.StringRegexTest3" \
"6 tests.StringLengthTest1" \
"6 tests.StringLengthTest2" \
"5 tests.StringLengthTest3" \
"3 tests.StringMapTest" \
"4 tests.LongTest" \
"7 tests.LongTest3" \
"4 tests.TimestampTest" \
"4 tests.Testme" \
"4 tests.TestmeInteger" \
"4 tests.TestmeLong" \
"5 tests.NonLinear" \
"5 tests.BoolTest2" \
"8 tests.SwitchTest" \
"3 tests.SwitchTest2" \
"5 tests.Linear" \
"3 tests.SimpleDBTest" \
"17 tests.DBUpdateTest" \
"17 tests.DBSelectTest" \
"17 tests.DBDeleteTest" \
"17 tests.DBDateSelectTest" \
"6 tests.DBInsertTest" \
"5 tests.OrAssumptionTest" \
"4 tests.OrAssumptionTest2" \
"5 tests.AssertIfPossibleTest1" \
"10 tests.AssertIfPossibleTest2" \
"79 tests.ManyColumnsOrRecords" \
"7 tests.AbstractionTest1" \
"14 tests.AbstractionTest2" \
"14 tests.SortedListInsert2" \
"11 tests.SortedListInsert3" \
"7 tests.DataAnnotation6" \
"5 tests.DataAnnotation7" \
"29 tests.QSort" \
"29 tests.QSortLong" \
"25 tests.InsertionSort" \
"121 tests.MergeSortJDK15" \
"76 tests.BinaryTreeSearch" \
"13 tests.HeapInsertJDK15" \
"25 tests.QuickSortJDK15" \
"121 tests.SortedListInsert" \
"206 tests.BellmanFord" \
"157 tests.Dijkstra"\
)
  rm test.log

  for ((i=0; i<${#all_tests[*]}; i++)); do
    local args="${all_tests[$i]}"
    ${SCRIPT_DIR}/concolic.sh $args
  done

  cat test.log
}


main $@
