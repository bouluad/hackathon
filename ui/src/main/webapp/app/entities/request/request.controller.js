(function() {
    'use strict';

    angular
        .module('uiApp')
        .controller('RequestController', RequestController);

    RequestController.$inject = ['Request', 'Principal'];

    function RequestController(Request, Principal) {

        var vm = this;

        vm.requests = [];

        loadAll();

        function loadAll() {
            Request.query(function(result) {
                vm.requests = result;
                // show results
                Principal.identity().then(function (user) {
                    console.log(user.authorities.indexOf("ROLE_USER"))
                    if (user.authorities.indexOf("ROLE_ADMIN") === -1) {
                        vm.requests = vm.requests.filter(function (request) {
                            console.log(request.user, user.login)
                            return request.user === user.login;
                        });
                    }
                });
                vm.searchQuery = null;
            });
        }
    }
})();
