var testApp = angular.module("testApp", ["ngResource"]);

testApp.controller(
    "mainController", 
    ["$scope", "$resource", 
     function($scope, $resource) {
	 var TestResource = $resource('/json/test/:ID', 
				      {ID: "@ID"}); 

	 $scope.tests = [];
	 $scope.nextID = 0;
	 
	 $scope.newID = function() {
             var id = $scope.nextID;
             $scope.nextID += 1;
             return id;
	 }
	 
	 $scope.addTest = function() {
             if ($scope.url) {
		 var url = $scope.url;
		 var id = $scope.newID();

		 var newTest = {"ID": id,
				"url": url,
				"state": "START"};
		 $scope.tests.push(newTest);
		 $scope.url = "";

		 var newTestResource = new TestResource(newTest);
		 newTestResource.$save();
             }
	 }


	 $scope.loadData = function() {
	     TestResource.query(function(tests) {
		 console.log(tests);
		 $scope.tests = tests;
	     })
	     $scope.nextID = 1;
	 }
	 
	$scope.loadData();			  
}]);
