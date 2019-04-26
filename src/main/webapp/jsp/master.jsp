<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dg_master').edatagrid('saveRow');
                $('#dd_master').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                $('#dg_master').edatagrid('saveRow');
                //alert('修改成功')
                $("#dg_master").edatagrid("reload")
            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                //alert('帮助按钮')
                $('#dg_master').edatagrid('destroyRow');
                //$("#dg_banner").edatagrid("reload")
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {
                $('#dg_master').edatagrid('saveRow');
                $("#dg_master").edatagrid("reload")
            }
        },'-', {
            iconCls: 'icon-save',
            text: '下载表格',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/banner/downloadExcel"
            }
        }];

        $('#dg_master').edatagrid({
            url: '${pageContext.request.contextPath}/master/queryByPage',
            saveUrl: '${pageContext.request.contextPath}/master/update',
            updateUrl:'${pageContext.request.contextPath}/master/update',
            destroyUrl: '${pageContext.request.contextPath}/master/delete',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 15, 20, 30],
            columns: [[
                {field: 'id', title: '上师编号', width: 100},
                {field: 'dharma', title: '法号', width: 100, editor:{
                        type: 'text',
                        options: {required: true}
                    }
                },
                {field: 'status', title: '状态', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
            ]],
            toolbar: tb,


            view: detailview,
            //rowIndex:行的索引
            //rowData ：行数据
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/服务器/' + rowData.headImg + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>上师法号: ' + rowData.title + '</p>' +
                    '<p>上师状态: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addBanner() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/master/insert',
            onSubmit: function () {

            },
            success: function (data) {
                alert(data)
                $("#ff").form("clear")
                $('#dd_master').dialog({
                    closed:true
                });
                $("#dg_master").edatagrid("reload")
            }
        });

    }

</script>
<table id="dg_master"></table>
<div id="dd_master" class="easyui-dialog" title="添加" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addMaster();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">上师法号:</label>
            <input id="name" class="easyui-validatebox" type="text" name="dharma" data-options="required:true"/>
        </div>
        <div>
            <label for="name">上传头像:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>

    </form>
</div>
