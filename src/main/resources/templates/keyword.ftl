<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${keyword}</title>
<style></style>
</head>
<body>
<h1>${keyword}</h1>

<table border="1">
  <tr>
  	<td>Doc</td>
  	<td>path</td>
  	<td>link</td>
  	<td>Page Start</td>
  	<td>Page End</td>
  	<td>text</td>
  	<td>pertinence</td>
  </tr>

<#list docpartInfos as info>
   
      <tr>
      	<td>${info.name}</td>
      	<td>${info.path}</td>
      	<td>${info.annotationName}</td>
      	<td>${info.pageStart}</td>
      	<td>${info.pageEnd}</td>
      	<td>${info.text}</td>
      	<td>${info.pertinence}</td>
      </tr>
    
</#list>
</table>

</body>
</html>
 