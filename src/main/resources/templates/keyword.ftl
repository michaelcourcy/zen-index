<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${keyword}</title>
<style></style>
</head>
<body>
<h1>${keyword}</h1>

<table border="1" width="100%">
  <tr>
  	<td>Document Name</td>
  	<td>Open odt</td>
  	<td>Go to the comment</td>
  	<td>Page Start</td>
  	<td>Page End</td>
  	<td>text</td>
  	<td>pertinence</td>
  	<td>extra information</td>
  </tr>

<#list docpartInfos as info>
   
      <tr>
      	<td>${info.name}</td>
      	<td><a href="${info.path}/${info.name}.odt">Odt</a></td>
      	<td><a href="${info.path}/${info.name}.html#${info.annotationName}_begin">Html</a></td>
      	<td>${info.pageStart}</td>
      	<td>${info.pageEnd}</td>
      	<td>${info.text}</td>
      	<td>${info.pertinence}</td>
      	<td>${info.extraInfo}</td>
      </tr>
    
</#list>
</table>

</body>
</html>
 