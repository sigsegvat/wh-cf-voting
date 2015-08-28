var whVotingApp = angular.module('whVotingApp', [
    'ngRoute',
    'indexController',
    'resultController'
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
                    controller: 'resultController'
                })
                .otherwise({
                    redirectTo: '/'
                })
            ;
        }]
);

whVotingApp.run(function ($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function () {
        $location.path("/");
        alert(1);
    });
});

var indexController = angular.module('indexController', []);
indexController.controller('indexController', ['$scope',
    function($scope) {
        alert(1);
    }])
;

var resultController = angular.module('resultController', []);
resultController.controller('resultController', ['$scope',
    function($scope) {
        alert(2);
    }])
;
