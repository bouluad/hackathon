(function () {
    'use strict';

    angular
        .module('uiApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('auth/api/register', {}, {});
    }
})();
