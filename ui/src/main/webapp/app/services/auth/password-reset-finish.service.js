(function() {
    'use strict';

    angular
        .module('uiApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('auth/api/account/reset-password/finish', {}, {});

        return service;
    }
})();
