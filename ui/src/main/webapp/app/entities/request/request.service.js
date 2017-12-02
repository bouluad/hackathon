(function() {
    'use strict';
    angular
        .module('uiApp')
        .factory('Request', Request);

    Request.$inject = ['$resource'];

    function Request ($resource) {
        var resourceUrl =  'carnetdordre/' + 'api/requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
