<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入url --%>
<button id="btnExport" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" title="导出"><i class="fa fa-file-excel-o"></i> 导出</button>
<script type="text/javascript">
$(document).ready(function() {

    $('#${id} thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
  	  $('#${id} tbody tr td input.i-checks').iCheck('check');
  	});

  	$('#${id} thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
  	  $('#${id} tbody tr td input.i-checks').iCheck('uncheck');
  	});
	
	
	$("#btnExport").click(function(){
		
		  var size = $("#${id} tbody tr td input.i-checks:checked").size();
		  if(size == 0 ){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
	
		  if(size > 1 ){
			top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
		
		  var str="";
		  var ids="";
		  $("#${id} tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		
		top.layer.confirm('确认要导出Excel吗?', {icon: 3, title:'系统提示'}, function(index){
		    //do something
	    	//导出之前备份
	    	var url =  $("#searchForm").attr("action");
	    	var pageNo =  $("#pageNo").val();
	    	var pageSize = $("#pageSize").val();
	    	//导出excel
	        $("#searchForm").attr("action","${url}?ids="+ids); 
		    $("#pageNo").val(-1);
			$("#pageSize").val(-1);
			$("#searchForm").submit();

			//导出excel之后还原
			$("#searchForm").attr("action",url);
		    $("#pageNo").val(pageNo);
			$("#pageSize").val(pageSize);
		    top.layer.close(index);
		});
	});
    
});


</script>