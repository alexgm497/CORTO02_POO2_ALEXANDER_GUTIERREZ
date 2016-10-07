$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consUsuaRole([{name : 'codiUsuaRolePara', value : row.id.trim()}]);
        });
        return false;
    };        
    INIT_OBJE_USUA_ROLE();
});

function INIT_OBJE_USUA_ROLE()
{
    $("#TablUsuaRole").initBootTable();
}