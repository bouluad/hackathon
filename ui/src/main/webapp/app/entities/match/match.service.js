(function() {
    'use strict';
    angular
        .module('uiApp')
        .factory('Match', Match);

    Match.$inject = ['$resource'];

    function Match ($resource) {
        var resourceUrl =  'carnetdordre/' + 'api/match/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
