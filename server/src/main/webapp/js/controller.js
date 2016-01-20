var testApp = angular.module("testApp", []);

testApp.controller("mainController", function($scope) {

    $scope.tests = [];
    $scope.nextID = 1;

    $scope.newID = function() {
        var id = $scope.nextID;
        $scope.nextID += 1;
        return id; 
    }

    $scope.addTest = function() {
        if ($scope.url) {
            var url = $scope.url;
            var id = $scope.newID();
            $scope.tests.push({"ID": id,
                               "url": url,
                               "state": "START"});
            $scope.url = "";
        }
        
    }
    

});
