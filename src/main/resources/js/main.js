
var whVotingApp = angular.module('whVotingApp', [
    'ngRoute'
]);

whVotingApp.config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
                when('/', {
                    templateUrl: 'tpl/index.tpl.html',
                    controller: 'indexController'
                }).
                when('/result', {
                    templateUrl: 'tpl/result.tpl.html',
                    controller: 'resultController',
                    resolve: {
                        access:  function($facebook) { return $facebook.loginStatus(); }
                    }
                });
        }]
);

whVotingApp.run(function ($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function () {
        $location.path("/");
    });
});

whVotingApp.controller('indexController', ['$scope',
    function($scope) {
        console.log("test");
    }
]);
