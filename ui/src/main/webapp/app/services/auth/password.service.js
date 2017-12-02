(function() {
    'use strict';

    angular
        .module('uiApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('auth/api/account/change-password', {}, {});

        return service;
    }
})();
