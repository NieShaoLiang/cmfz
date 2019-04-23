<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dg_banner').edatagrid('saveRow');
                $('#dd_banner').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                $('#dg_banner').edatagrid('saveRow');
                //alert('修改成功')

            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                //alert('帮助按钮')
                $('#dg_banner').edatagrid('destroyRow');

            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {
                $('#dg_banner').edatagrid('saveRow');

            }
        },'-', {
            iconCls: 'icon-save',
            text: '下载表格',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/banner/downloadExcel"
            }
        }];

        $('#dg_banner').edatagrid({
            url: '${pageContext.request.contextPath}/banner/queryAll',
            saveUrl: '${pageContext.request.contextPath}/banner/update',
            updateUrl:'${pageContext.request.contextPath}/banner/update',
            destroyUrl: '${pageContext.request.contextPath}/banner/delete',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 15, 20, 30],
            columns: [[
                {field: 'title', title: '标题', width: 100/*,editor: {
                        type: 'text',
                        options: {required: true}
                    }*/
                },
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
                {field: 'createDate', title: '日期', width: 100}
            ]],
            toolbar: tb,


            view: detailview,
            //rowIndex:行的索引
            //rowData ：行数据
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/服务器/' + rowData.imgPath + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>描述: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '<p>时间: ' + rowData.createDate + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addBanner() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/banner/insert',
            onSubmit: function () {

            },
            success: function (data) {
                alert(data)
                $('#dd_banner').dialog({
                    closed:true
                });
            }
        });

    }

</script>
<table id="dg_banner"></table>
<div id="dd_banner" class="easyui-dialog" title="添加" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">轮播图名称:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="name">上传图片:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>

    </form>
</div>
