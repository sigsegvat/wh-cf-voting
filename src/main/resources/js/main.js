var whVotingApp = angular.module('whVotingApp', [
    'ngRoute'
]);

whVotingApp.config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/result', {
                    templateUrl: 'tpl/result.tpl.html',
                    controller: 'resultController'
                }).
                when('/end', {
                    templateUrl: 'tpl/end.tpl.html',
                    controller: 'dummy'
                }).when('/:token', {
                    templateUrl: 'tpl/index.tpl.html',
                    controller: 'indexController'
                });
        }]
);

whVotingApp.run(function ($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function () {
        $location.path("/");
    });
});

whVotingApp.controller('dummy', [], function () {

});

whVotingApp.controller('indexController', ['$scope', '$http', '$routeParams', 'votingService',
    function ($scope, $http, $routeParams, votingService) {
        var token = $routeParams.token;

        $http.get('https://4k5an0qz1j.execute-api.eu-west-1.amazonaws.com/prod/options/' + token)
            .success(function (data) {
                $scope.votingModel = data[0];
                $scope.votingOptions = data;
                $scope.vote = function (project) {
                    votingService.vote(token, project);
                };
            })
            .error(function () {
                alert('An error occurred.');
            });
        console.log("test");
    }
]);

whVotingApp.service('votingService', function ($http, $location) {
    return {
        vote: function (token, project) {
            $http.get('https://4k5an0qz1j.execute-api.eu-west-1.amazonaws.com/prod/options?tokenId=' + token + '&vote=' + project);
            $location.path("/end");
        }
    };
});
