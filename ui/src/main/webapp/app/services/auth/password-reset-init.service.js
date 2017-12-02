(function() {
    'use strict';

    angular
        .module('uiApp')
        .factory('PasswordResetInit', PasswordResetInit);

    PasswordResetInit.$inject = ['$resource'];

    function PasswordResetInit($resource) {
        var service = $resource('auth/api/account/reset-password/init', {}, {});

        return service;
    }
})();
