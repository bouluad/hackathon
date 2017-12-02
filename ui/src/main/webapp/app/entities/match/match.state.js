(function() {
    'use strict';

    angular
        .module('uiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('match', {
                parent: 'entity',
                url: '/match',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Matches'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/match/matches.html',
                        controller: 'MatchController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            });
    }

})();
