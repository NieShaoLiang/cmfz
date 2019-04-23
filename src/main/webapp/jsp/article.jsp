<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dg_article').edatagrid('saveRow');
                $('#dd_article').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                $('#dg_article').edatagrid('saveRow');
                //alert('修改成功')

            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                //alert('帮助按钮')
                $('#dg_article').edatagrid('destroyRow');

            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {
                $('#dg_article').edatagrid('saveRow');

            }
        },'-', {
            iconCls: 'icon-save',
            text: '下载表格',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/banner/downloadExcel"
            }
        }];

        $('#dg_article').edatagrid({
            url: '${pageContext.request.contextPath}/article/selectAll',
            saveUrl: '${pageContext.request.contextPath}/article/update',
            updateUrl:'${pageContext.request.contextPath}/article/update',
            destroyUrl: '${pageContext.request.contextPath}/article/delete',
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
                    '<p>时间: ' + rowData.date + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addArticle() {
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
<table id="dg_article"></table>
<div id="dd_article" class="easyui-dialog" title="添加" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addArticle();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">文章名:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="name">上传图片:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>
        <div>
            <label for="name">上师id:</label>
            <input id="name" class="easyui-validatebox" type="text" name="masterId" data-options="required:true"/>
        </div>

    </form>
</div>
