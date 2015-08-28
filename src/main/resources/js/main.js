var whVotingApp = angular.module('whVotingApp', [
    'ngRoute'
]);

whVotingApp.config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/:token', {
                    templateUrl: 'tpl/index.tpl.html',
                    controller: 'indexController'
                }).
                when('/result', {
                    templateUrl: 'tpl/result.tpl.html',
                    controller: 'resultController'
                });
        }]
);

whVotingApp.run(function ($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function () {
        $location.path("/");
    });
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

whVotingApp.service('votingService', function ($http) {
    return {
        vote: function (token, project) {
            $http.put('https://4k5an0qz1j.execute-api.eu-west-1.amazonaws.com/prod/options/' + token + "/" + project)
                .success(function() {
                    alert('Voting successful!');
                })
                .error(function() {
                    alert('Voting failed!!');
                });
        }
    };
});
