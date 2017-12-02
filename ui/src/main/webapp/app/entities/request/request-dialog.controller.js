(function() {
    'use strict';

    angular
        .module('uiApp')
        .controller('RequestDialogController', RequestDialogController);

    RequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Request', 'Product'];

    function RequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Request, Product) {
        var vm = this;

        vm.request = entity;
        vm.clear = clear;
        vm.save = save;

        vm.products = [];

        Product.query(function(result) {
            vm.products = result.filter(function (value, index, self) {
                return self.indexOf(value) === index;
            });
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.request.id !== null) {
                Request.update(vm.request, onSaveSuccess, onSaveError);
            } else {
                Request.save(vm.request, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('uiApp:requestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
