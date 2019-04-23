<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dg_user').edatagrid('saveRow');
                $('#dd_user').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                $('#dg_user').edatagrid('saveRow');
                //alert('修改成功')

            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                //alert('帮助按钮')
                $('#dg_user').edatagrid('destroyRow');

            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {
                $('#dg_user').edatagrid('saveRow');

            }
        }];

        $('#dg_user').edatagrid({
            url: '${pageContext.request.contextPath}/user/selectAll',
            saveUrl: '${pageContext.request.contextPath}/user/update',
            updateUrl:'${pageContext.request.contextPath}/user/update',
            destroyUrl: '${pageContext.request.contextPath}/user/delete',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 15, 20, 30],
            columns: [[
                {field: 'name', title: '用户名', width: 100},
                {field: 'dharma', title: '法号', width: 100},
                {field: 'createDate', title: '注册日期', width: 100},
                {field: 'phone', title: '手机', width: 100,editor:{
                        type: 'text',
                        options: {required: true}
                    }},
                {field: 'status', title: '状态', width: 100,editor:{
                        type: 'text',
                        options: {required: true}
                    }},
                {field: 'province', title: '省份', width: 100,editor:{
                        type: 'text',
                        options: {required: true}
                    }},
                {field: 'city', title: '城市', width: 100,editor:{
                        type: 'text',
                        options: {required: true}
                    }}
            ]],
            toolbar: tb,


            view: detailview,
            //rowIndex:行的索引
            //rowData ：行数据
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/服务器/' + rowData.headImg + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>用户名: ' + rowData.name + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '<p>手机号: ' + rowData.phone + '</p>' +
                    '<p>创建时间: ' + rowData.createDate + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addUser() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/user/insert',
            onSubmit: function () {

            },
            success: function (data) {
                alert(data)
                $('#dd_user').dialog({
                    closed:true
                });
            }
        });

    }

</script>
<table id="dg_user"></table>
<div id="dd_user" class="easyui-dialog" title="添加" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addUser();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">用户名:</label>
            <input id="name" class="easyui-validatebox" type="text" name="name" data-options="required:true"/>
        </div>
        <div>
            <label for="name">密码:</label>
            <input id="password" class="easyui-validatebox" type="text" name="password" data-options="required:true"/>
        </div>
        <div>
            <label for="name">法号:</label>
            <input id="dharma" class="easyui-validatebox" type="text" name="dharma" data-options="required:true"/>
        </div>
        <div>
            <label for="name">手机号:</label>
            <input id="phone" class="easyui-validatebox" type="text" name="phone" data-options="required:true"/>
        </div>
        <div>
            <label for="name">上传头像:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>

    </form>
</div>
