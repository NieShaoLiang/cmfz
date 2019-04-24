<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-edit',
            text: '专辑详情',
            handler: function () {
               var album = $("#tt_album").treegrid('getSelected');
               //console.log(album);
               //var aid = album.id;
               //console.log(aid)
               if (album==null||album.author==null){
                   alert("请选中专辑查看专辑详情")
               } else {
                   $.ajax({
                       type:'get',
                       data:album,
                       url:"${pageContext.request.contextPath}/album/selectOne",
                       dataType:"json",
                       success:function (data) {
                           console.log(data)
                           $("#album_amount").html(data.amount)
                           $("#album_author").html(data.author)
                           $("#album_brief").html(data.brief)
                           $("#album_imgPath").prop("src","${pageContext.request.contextPath}/服务器/"+data.imgPath)
                           $("#ablum_boardcast").html(data.boardcast)
                           $("#album_title").html(data.title)
                           $("#album_publishDate").html(data.publishDate)
                           $("#album_score").html(data.score)
                           $("#one_album").dialog("open")
                       }
                   })
               }
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加专辑',
            handler: function () {
                $('#add_album').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加章节',
            handler: function () {
                var album = $("#tt_album").treegrid('getSelected');
                console.log(album);
                if (album==null||album.author==null){
                    alert("请选择专辑再添加章节")
                } else {
                    $("#albumId").val(album.id)
                    $('#add_chapter').dialog('open');
                }
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '下载章节',
            handler: function () {
                var chapter = $("#tt_album").treegrid('getSelected');
                console.log(chapter);
                if (chapter==null||chapter.path==null){
                    alert("请选择您要下载的章节")
                } else {
                    location.href="${pageContext.request.contextPath}/chapter/download?path="+chapter.path+"&title="+chapter.title;
                }
            }
        }];
/*查看所有专辑  start-----*/
        $('#tt_album').treegrid({
            url:'${pageContext.request.contextPath}/album/queryAll',
            idField:'id',
            treeField:'title',
            toolbar:tb,
            fit:true,
            fitColumn:true,
            columns:[[
                {title:'名字',field:'title',width:180},
                {field:'size',title:'章节大小',width:60,},
                {field:'duration',title:'章节时长',width:80}
             ]]
        });
/*查看所有专辑 end-----*/
/*查看专辑详情 start----*/
        $("#one_album").dialog({
            title: '专辑详情',
            width: 400,
            height: 200,
            closed: true,
            cache: false,
            href: '',
            modal: true
        });
/*查看专辑详情 end----*/
    })
/*添加专辑start*/
    function addAlbum() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/album/insert',
            onSubmit: function () {

            },
            success: function (data) {
                alert(data)
                $('#add_album').dialog({
                    closed:true
                });
                //重新加载页面
                $("#tt_album").treegrid("reload")
            }
        });
    }
/*添加专辑end*/
/*添加章节  start---*/
    function addChapter() {
        $('#gg').form('submit', {
            url: '${pageContext.request.contextPath}/chapter/add',
            onSubmit: function () {

            },
            success: function (data) {
                alert(data)
                $('#add_chapter').dialog({
                    closed:true
                });
                //重新加载页面
                $("#tt_album").treegrid("reload")
            }
        });
    }
/*添加章节  end---*/


</script>
<table id="tt_album" style="width:600px;height:400px"></table>
<%--专辑详情对话框--%>
<div id="one_album" class="easyui_dialog">
    <div><img id="album_imgPath" style="width: 75px;height: 50px"/> </div>
    专辑名称：<span id="album_title"></span>   &nbsp;&nbsp;   作者： <span id="album_author"></span><br/>
    播音：<span id="ablum_boardcast"></span>   &nbsp;&nbsp;  发布日期：<span id="album_publishDate"></span><br/>
    章节数：<span id="album_amount"></span><br/>    &nbsp;&nbsp;    评分：<span id="album_score"></span><br/>
    简介：<span id="album_brief"></span>
</div>
<%--添加专辑对话框--%>
<div id="add_album" class="easyui-dialog" title="添加专辑" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addAlbum();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">
    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">专辑名称:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="name">作者:</label>
            <input id="name" class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="name">播音:</label>
            <input id="name" class="easyui-validatebox" type="text" name="boardcast" data-options="required:true"/>
        </div>
        <div>
            <label for="name">简介:</label>
            <input id="name" class="easyui-validatebox" type="text" name="brief" data-options="required:true"/>
        </div>
        <div>
            <label for="name">封面图像:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>
    </form>
</div>
<%--添加章节对话框--%>
<div id="add_chapter" class="easyui-dialog" title="添加章节" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addChapter();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="gg" method="post" enctype="multipart/form-data">
        <input type="hidden" name="albumId" id="albumId">
        <div>
            <label for="name">章节名称:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="name">上传文件:</label>
            <input class="easyui-filebox" name="file" id="file" style="width:150px">
        </div>
    </form>
</div>