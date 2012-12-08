



<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
 
 <meta name="ROBOTS" content="NOARCHIVE">
 
 <link rel="icon" type="image/vnd.microsoft.icon" href="http://www.gstatic.com/codesite/ph/images/phosting.ico">
 
 
 <script type="text/javascript">
 
 
 
 
 var codesite_token = "tcpwL7AGS4MLuCtpbyV-QdgUqPs:1354928002761";
 
 
 var CS_env = {"loggedInUserEmail":"zy26@duke.edu","relativeBaseUrl":"","projectHomeUrl":"/p/vooga-compsci308-fall2012","assetVersionPath":"http://www.gstatic.com/codesite/ph/7707091683928850954","assetHostPath":"http://www.gstatic.com/codesite/ph","domainName":null,"projectName":"vooga-compsci308-fall2012","token":"tcpwL7AGS4MLuCtpbyV-QdgUqPs:1354928002761","profileUrl":"/u/zy26@duke.edu/"};
 var _gaq = _gaq || [];
 _gaq.push(
 ['siteTracker._setAccount', 'UA-18071-1'],
 ['siteTracker._trackPageview']);
 
 (function() {
 var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
 ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
 (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
 })();
 
 </script>
 
 
 <title>Start.java - 
 vooga-compsci308-fall2012 -
 
 
 CompSci 308 Fall 2012 course project: VOOGA - Google Project Hosting
 </title>
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/7707091683928850954/css/core.css">
 
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/7707091683928850954/css/ph_detail.css" >
 
 
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/7707091683928850954/css/d_sb.css" >
 
 
 
<!--[if IE]>
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/7707091683928850954/css/d_ie.css" >
<![endif]-->
 <style type="text/css">
 .menuIcon.off { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -42px }
 .menuIcon.on { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -28px }
 .menuIcon.down { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 0; }
 
 
 
  tr.inline_comment {
 background: #fff;
 vertical-align: top;
 }
 div.draft, div.published {
 padding: .3em;
 border: 1px solid #999; 
 margin-bottom: .1em;
 font-family: arial, sans-serif;
 max-width: 60em;
 }
 div.draft {
 background: #ffa;
 } 
 div.published {
 background: #e5ecf9;
 }
 div.published .body, div.draft .body {
 padding: .5em .1em .1em .1em;
 max-width: 60em;
 white-space: pre-wrap;
 white-space: -moz-pre-wrap;
 white-space: -pre-wrap;
 white-space: -o-pre-wrap;
 word-wrap: break-word;
 font-size: 1em;
 }
 div.draft .actions {
 margin-left: 1em;
 font-size: 90%;
 }
 div.draft form {
 padding: .5em .5em .5em 0;
 }
 div.draft textarea, div.published textarea {
 width: 95%;
 height: 10em;
 font-family: arial, sans-serif;
 margin-bottom: .5em;
 }

 
 .nocursor, .nocursor td, .cursor_hidden, .cursor_hidden td {
 background-color: white;
 height: 2px;
 }
 .cursor, .cursor td {
 background-color: darkblue;
 height: 2px;
 display: '';
 }
 
 
.list {
 border: 1px solid white;
 border-bottom: 0;
}

 
 </style>
</head>
<body class="t4">
<script type="text/javascript">
 window.___gcfg = {lang: 'en'};
 (function() 
 {var po = document.createElement("script");
 po.type = "text/javascript"; po.async = true;po.src = "https://apis.google.com/js/plusone.js";
 var s = document.getElementsByTagName("script")[0];
 s.parentNode.insertBefore(po, s);
 })();
</script>
<div class="headbg">

 <div id="gaia">
 

 <span>
 
 
 
 <b>zy26@duke.edu</b>
 
 
 | <a href="/u/zy26@duke.edu/" id="projects-dropdown" onclick="return false;"
 ><u>My favorites</u> <small>&#9660;</small></a>
 | <a href="/u/zy26@duke.edu/" onclick="_CS_click('/gb/ph/profile');"
 title="Profile, Updates, and Settings"
 ><u>Profile</u></a>
 | <a href="https://www.google.com/accounts/Logout?continue=http%3A%2F%2Fcode.google.com%2Fp%2Fvooga-compsci308-fall2012%2Fsource%2Fbrowse%2Fsrc%2Fvooga%2Fturnbased%2FStart.java" 
 onclick="_CS_click('/gb/ph/signout');"
 ><u>Sign out</u></a>
 
 </span>

 </div>

 <div class="gbh" style="left: 0pt;"></div>
 <div class="gbh" style="right: 0pt;"></div>
 
 
 <div style="height: 1px"></div>
<!--[if lte IE 7]>
<div style="text-align:center;">
Your version of Internet Explorer is not supported. Try a browser that
contributes to open source, such as <a href="http://www.firefox.com">Firefox</a>,
<a href="http://www.google.com/chrome">Google Chrome</a>, or
<a href="http://code.google.com/chrome/chromeframe/">Google Chrome Frame</a>.
</div>
<![endif]-->



 <table style="padding:0px; margin: 0px 0px 10px 0px; width:100%" cellpadding="0" cellspacing="0"
 itemscope itemtype="http://schema.org/CreativeWork">
 <tr style="height: 58px;">
 
 
 
 <td id="plogo">
 <link itemprop="url" href="/p/vooga-compsci308-fall2012">
 <a href="/p/vooga-compsci308-fall2012/">
 
 <img src="http://www.gstatic.com/codesite/ph/images/defaultlogo.png" alt="Logo" itemprop="image">
 
 </a>
 </td>
 
 <td style="padding-left: 0.5em">
 
 <div id="pname">
 <a href="/p/vooga-compsci308-fall2012/"><span itemprop="name">vooga-compsci308-fall2012</span></a>
 </div>
 
 <div id="psum">
 <a id="project_summary_link"
 href="/p/vooga-compsci308-fall2012/"><span itemprop="description">CompSci 308 Fall 2012 course project: VOOGA</span></a>
 
 </div>
 
 
 </td>
 <td style="white-space:nowrap;text-align:right; vertical-align:bottom;">
 
 <form action="/hosting/search">
 <input size="30" name="q" value="" type="text">
 
 <input type="submit" name="projectsearch" value="Search projects" >
 </form>
 
 </tr>
 </table>

</div>

 
<div id="mt" class="gtb"> 
 <a href="/p/vooga-compsci308-fall2012/" class="tab ">Project&nbsp;Home</a>
 
 
 
 
 <a href="/p/vooga-compsci308-fall2012/downloads/list" class="tab ">Downloads</a>
 
 
 
 
 
 <a href="/p/vooga-compsci308-fall2012/w/list" class="tab ">Wiki</a>
 
 
 
 
 
 <a href="/p/vooga-compsci308-fall2012/issues/list"
 class="tab ">Issues</a>
 
 
 
 
 
 <a href="/p/vooga-compsci308-fall2012/source/checkout"
 class="tab active">Source</a>
 
 
 
 
 
 
 
 
 
 
 <div class=gtbc></div>
</div>
<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" class="st">
 <tr>
 
 
 
 
 
 
 
 <td class="subt">
 <div class="st2">
 <div class="isf">
 
 <form action="/p/vooga-compsci308-fall2012/source/browse" style="display: inline">
 
 Repository:
 <select name="repo" id="repo" style="font-size: 92%" onchange="submit()">
 <option value="default">default</option><option value="wiki">wiki</option>
 </select>
 </form>
 
 


 <span class="inst1"><a href="/p/vooga-compsci308-fall2012/source/checkout">Checkout</a></span> &nbsp;
 <span class="inst2"><a href="/p/vooga-compsci308-fall2012/source/browse/">Browse</a></span> &nbsp;
 <span class="inst3"><a href="/p/vooga-compsci308-fall2012/source/list">Changes</a></span> &nbsp;
 <span class="inst4"><a href="/p/vooga-compsci308-fall2012/source/clones">Clones</a></span> &nbsp; 
 &nbsp;
 
 
 <form action="/p/vooga-compsci308-fall2012/source/search" method="get" style="display:inline"
 onsubmit="document.getElementById('codesearchq').value = document.getElementById('origq').value">
 <input type="hidden" name="q" id="codesearchq" value="">
 <input type="text" maxlength="2048" size="38" id="origq" name="origq" value="" title="Google Code Search" style="font-size:92%">&nbsp;<input type="submit" value="Search Trunk" name="btnG" style="font-size:92%">
 
 
 
 
 <a href="/p/vooga-compsci308-fall2012/issues/entry?show=review&former=sourcelist">Request code review</a>
 
 
 
 </form>
 <script type="text/javascript">
 
 function codesearchQuery(form) {
 var query = document.getElementById('q').value;
 if (query) { form.action += '%20' + query; }
 }
 </script>
 </div>
</div>

 </td>
 
 
 
 <td align="right" valign="top" class="bevel-right"></td>
 </tr>
</table>


<script type="text/javascript">
 var cancelBubble = false;
 function _go(url) { document.location = url; }
</script>
<div id="maincol"
 
>

 




<div class="expand">
<div id="colcontrol">
<style type="text/css">
 #file_flipper { white-space: nowrap; padding-right: 2em; }
 #file_flipper.hidden { display: none; }
 #file_flipper .pagelink { color: #0000CC; text-decoration: underline; }
 #file_flipper #visiblefiles { padding-left: 0.5em; padding-right: 0.5em; }
</style>
<table id="nav_and_rev" class="list"
 cellpadding="0" cellspacing="0" width="100%">
 <tr>
 
 <td nowrap="nowrap" class="src_crumbs src_nav" width="33%">
 <strong class="src_nav">Source path:&nbsp;</strong>
 <span id="crumb_root">
 
 <a href="/p/vooga-compsci308-fall2012/source/browse/">git</a>/&nbsp;</span>
 <span id="crumb_links" class="ifClosed"><a href="/p/vooga-compsci308-fall2012/source/browse/src/">src</a><span class="sp">/&nbsp;</span><a href="/p/vooga-compsci308-fall2012/source/browse/src/vooga/">vooga</a><span class="sp">/&nbsp;</span><a href="/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/">turnbased</a><span class="sp">/&nbsp;</span>Start.java</span>
 
 
 
 
 
 <form class="src_nav">
 
 <span class="sourcelabel"><strong>Branch:</strong>
 <select id="branch_select" name="name" onchange="submit()">
 
 <option value="master"
 selected>
 master
 </option>
 
 <option value="platformer"
 >
 platformer
 </option>
 
 <option value="turnbased"
 >
 turnbased
 </option>
 
 <option value="turnbased3"
 >
 turnbased3
 </option>
 
 <option value="turnbased4"
 >
 turnbased4
 </option>
 
 
 </select>
 </span>
 </form>
 
 
 
 
 


 <span class="sourcelabel">Download <a href="//vooga-compsci308-fall2012.googlecode.com/archive/5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5.zip">zip</a></span>


 </td>
 
 
 <td nowrap="nowrap" width="33%" align="center">
 <a href="/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/Start.java?edit=1"
 ><img src="http://www.gstatic.com/codesite/ph/images/pencil-y14.png"
 class="edit_icon">Edit file</a>
 </td>
 
 
 <td nowrap="nowrap" width="33%" align="right">
 <table cellpadding="0" cellspacing="0" style="font-size: 100%"><tr>
 
 
 <td class="flipper"><b>5f7f3bbd57a8</b></td>
 
 </tr></table>
 </td> 
 </tr>
</table>

<div class="fc">
 
 
 
<style type="text/css">
.undermouse span {
 background-image: url(http://www.gstatic.com/codesite/ph/images/comments.gif); }
</style>
<table class="opened" id="review_comment_area"
onmouseout="gutterOut()"><tr>
<td id="nums">
<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>
<pre><table width="100%" id="nums_table_0"><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_1"

 onmouseover="gutterOver(1)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',1);">&nbsp;</span
></td><td id="1"><a href="#1">1</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_2"

 onmouseover="gutterOver(2)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',2);">&nbsp;</span
></td><td id="2"><a href="#2">2</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_3"

 onmouseover="gutterOver(3)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',3);">&nbsp;</span
></td><td id="3"><a href="#3">3</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_4"

 onmouseover="gutterOver(4)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',4);">&nbsp;</span
></td><td id="4"><a href="#4">4</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_5"

 onmouseover="gutterOver(5)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',5);">&nbsp;</span
></td><td id="5"><a href="#5">5</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_6"

 onmouseover="gutterOver(6)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',6);">&nbsp;</span
></td><td id="6"><a href="#6">6</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_7"

 onmouseover="gutterOver(7)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',7);">&nbsp;</span
></td><td id="7"><a href="#7">7</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_8"

 onmouseover="gutterOver(8)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',8);">&nbsp;</span
></td><td id="8"><a href="#8">8</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_9"

 onmouseover="gutterOver(9)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',9);">&nbsp;</span
></td><td id="9"><a href="#9">9</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_10"

 onmouseover="gutterOver(10)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',10);">&nbsp;</span
></td><td id="10"><a href="#10">10</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_11"

 onmouseover="gutterOver(11)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',11);">&nbsp;</span
></td><td id="11"><a href="#11">11</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_12"

 onmouseover="gutterOver(12)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',12);">&nbsp;</span
></td><td id="12"><a href="#12">12</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_13"

 onmouseover="gutterOver(13)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',13);">&nbsp;</span
></td><td id="13"><a href="#13">13</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_14"

 onmouseover="gutterOver(14)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',14);">&nbsp;</span
></td><td id="14"><a href="#14">14</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_15"

 onmouseover="gutterOver(15)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',15);">&nbsp;</span
></td><td id="15"><a href="#15">15</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_16"

 onmouseover="gutterOver(16)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',16);">&nbsp;</span
></td><td id="16"><a href="#16">16</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_17"

 onmouseover="gutterOver(17)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',17);">&nbsp;</span
></td><td id="17"><a href="#17">17</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_18"

 onmouseover="gutterOver(18)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',18);">&nbsp;</span
></td><td id="18"><a href="#18">18</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_19"

 onmouseover="gutterOver(19)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',19);">&nbsp;</span
></td><td id="19"><a href="#19">19</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_20"

 onmouseover="gutterOver(20)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',20);">&nbsp;</span
></td><td id="20"><a href="#20">20</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_21"

 onmouseover="gutterOver(21)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',21);">&nbsp;</span
></td><td id="21"><a href="#21">21</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_22"

 onmouseover="gutterOver(22)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',22);">&nbsp;</span
></td><td id="22"><a href="#22">22</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_23"

 onmouseover="gutterOver(23)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',23);">&nbsp;</span
></td><td id="23"><a href="#23">23</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_24"

 onmouseover="gutterOver(24)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',24);">&nbsp;</span
></td><td id="24"><a href="#24">24</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_25"

 onmouseover="gutterOver(25)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',25);">&nbsp;</span
></td><td id="25"><a href="#25">25</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_26"

 onmouseover="gutterOver(26)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',26);">&nbsp;</span
></td><td id="26"><a href="#26">26</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_27"

 onmouseover="gutterOver(27)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',27);">&nbsp;</span
></td><td id="27"><a href="#27">27</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_28"

 onmouseover="gutterOver(28)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',28);">&nbsp;</span
></td><td id="28"><a href="#28">28</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_29"

 onmouseover="gutterOver(29)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',29);">&nbsp;</span
></td><td id="29"><a href="#29">29</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_30"

 onmouseover="gutterOver(30)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',30);">&nbsp;</span
></td><td id="30"><a href="#30">30</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_31"

 onmouseover="gutterOver(31)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',31);">&nbsp;</span
></td><td id="31"><a href="#31">31</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_32"

 onmouseover="gutterOver(32)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',32);">&nbsp;</span
></td><td id="32"><a href="#32">32</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_33"

 onmouseover="gutterOver(33)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',33);">&nbsp;</span
></td><td id="33"><a href="#33">33</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_34"

 onmouseover="gutterOver(34)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',34);">&nbsp;</span
></td><td id="34"><a href="#34">34</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_35"

 onmouseover="gutterOver(35)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',35);">&nbsp;</span
></td><td id="35"><a href="#35">35</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_36"

 onmouseover="gutterOver(36)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',36);">&nbsp;</span
></td><td id="36"><a href="#36">36</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_37"

 onmouseover="gutterOver(37)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',37);">&nbsp;</span
></td><td id="37"><a href="#37">37</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_38"

 onmouseover="gutterOver(38)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',38);">&nbsp;</span
></td><td id="38"><a href="#38">38</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_39"

 onmouseover="gutterOver(39)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',39);">&nbsp;</span
></td><td id="39"><a href="#39">39</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_40"

 onmouseover="gutterOver(40)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',40);">&nbsp;</span
></td><td id="40"><a href="#40">40</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_41"

 onmouseover="gutterOver(41)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',41);">&nbsp;</span
></td><td id="41"><a href="#41">41</a></td></tr
><tr id="gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_42"

 onmouseover="gutterOver(42)"

><td><span title="Add comment" onclick="codereviews.startEdit('svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5',42);">&nbsp;</span
></td><td id="42"><a href="#42">42</a></td></tr
></table></pre>
<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>
</td>
<td id="lines">
<pre><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>
<pre class="prettyprint lang-java"><table id="src_table_0"><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_1

 onmouseover="gutterOver(1)"

><td class="source">package vooga.turnbased;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_2

 onmouseover="gutterOver(2)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_3

 onmouseover="gutterOver(3)"

><td class="source">import java.awt.Image;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_4

 onmouseover="gutterOver(4)"

><td class="source">import java.util.ArrayList;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_5

 onmouseover="gutterOver(5)"

><td class="source">import java.util.List;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_6

 onmouseover="gutterOver(6)"

><td class="source">import vooga.turnbased.gui.GameWindow;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_7

 onmouseover="gutterOver(7)"

><td class="source">import arcade.IArcadeGame;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_8

 onmouseover="gutterOver(8)"

><td class="source">import arcade.gamemanager.GameSaver;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_9

 onmouseover="gutterOver(9)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_10

 onmouseover="gutterOver(10)"

><td class="source">public class Start implements IArcadeGame {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_11

 onmouseover="gutterOver(11)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_12

 onmouseover="gutterOver(12)"

><td class="source">    @Override<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_13

 onmouseover="gutterOver(13)"

><td class="source">    public String getDescription () {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_14

 onmouseover="gutterOver(14)"

><td class="source">        return &quot;This is the turnbased RPG game.&quot;;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_15

 onmouseover="gutterOver(15)"

><td class="source">    }<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_16

 onmouseover="gutterOver(16)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_17

 onmouseover="gutterOver(17)"

><td class="source">    @Override<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_18

 onmouseover="gutterOver(18)"

><td class="source">    public Image getMainImage () {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_19

 onmouseover="gutterOver(19)"

><td class="source">        return GameWindow.importImage(&quot;z2i9V.gif&quot;);<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_20

 onmouseover="gutterOver(20)"

><td class="source">    }<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_21

 onmouseover="gutterOver(21)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_22

 onmouseover="gutterOver(22)"

><td class="source">    @Override<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_23

 onmouseover="gutterOver(23)"

><td class="source">    public String getName () {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_24

 onmouseover="gutterOver(24)"

><td class="source">        return &quot;Turnbased RPG&quot;;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_25

 onmouseover="gutterOver(25)"

><td class="source">    }<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_26

 onmouseover="gutterOver(26)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_27

 onmouseover="gutterOver(27)"

><td class="source">    @Override<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_28

 onmouseover="gutterOver(28)"

><td class="source">    public List&lt;Image&gt; getScreenshots () {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_29

 onmouseover="gutterOver(29)"

><td class="source">        List&lt;Image&gt; someImages = new ArrayList&lt;Image&gt;();<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_30

 onmouseover="gutterOver(30)"

><td class="source">        someImages.add(GameWindow.importImage(&quot;something.png&quot;));<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_31

 onmouseover="gutterOver(31)"

><td class="source">        someImages.add(GameWindow.importImage(&quot;377.gif&quot;));<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_32

 onmouseover="gutterOver(32)"

><td class="source">        return someImages;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_33

 onmouseover="gutterOver(33)"

><td class="source">    }<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_34

 onmouseover="gutterOver(34)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_35

 onmouseover="gutterOver(35)"

><td class="source">    @Override<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_36

 onmouseover="gutterOver(36)"

><td class="source">    public void runGame (String userPreferences, GameSaver s) {<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_37

 onmouseover="gutterOver(37)"

><td class="source">        int WIDTH = 800;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_38

 onmouseover="gutterOver(38)"

><td class="source">        int HEIGHT = 600;<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_39

 onmouseover="gutterOver(39)"

><td class="source">        GameWindow myGameWindow = new GameWindow(&quot;Turn-Based RPG&quot;, &quot;GameSetting&quot;, WIDTH, HEIGHT);        <br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_40

 onmouseover="gutterOver(40)"

><td class="source">    }<br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_41

 onmouseover="gutterOver(41)"

><td class="source"><br></td></tr
><tr
id=sl_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_42

 onmouseover="gutterOver(42)"

><td class="source">}<br></td></tr
></table></pre>
<pre><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>
</td>
</tr></table>

 
<script type="text/javascript">
 var lineNumUnderMouse = -1;
 
 function gutterOver(num) {
 gutterOut();
 var newTR = document.getElementById('gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_' + num);
 if (newTR) {
 newTR.className = 'undermouse';
 }
 lineNumUnderMouse = num;
 }
 function gutterOut() {
 if (lineNumUnderMouse != -1) {
 var oldTR = document.getElementById(
 'gr_svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5_' + lineNumUnderMouse);
 if (oldTR) {
 oldTR.className = '';
 }
 lineNumUnderMouse = -1;
 }
 }
 var numsGenState = {table_base_id: 'nums_table_'};
 var srcGenState = {table_base_id: 'src_table_'};
 var alignerRunning = false;
 var startOver = false;
 function setLineNumberHeights() {
 if (alignerRunning) {
 startOver = true;
 return;
 }
 numsGenState.chunk_id = 0;
 numsGenState.table = document.getElementById('nums_table_0');
 numsGenState.row_num = 0;
 if (!numsGenState.table) {
 return; // Silently exit if no file is present.
 }
 srcGenState.chunk_id = 0;
 srcGenState.table = document.getElementById('src_table_0');
 srcGenState.row_num = 0;
 alignerRunning = true;
 continueToSetLineNumberHeights();
 }
 function rowGenerator(genState) {
 if (genState.row_num < genState.table.rows.length) {
 var currentRow = genState.table.rows[genState.row_num];
 genState.row_num++;
 return currentRow;
 }
 var newTable = document.getElementById(
 genState.table_base_id + (genState.chunk_id + 1));
 if (newTable) {
 genState.chunk_id++;
 genState.row_num = 0;
 genState.table = newTable;
 return genState.table.rows[0];
 }
 return null;
 }
 var MAX_ROWS_PER_PASS = 1000;
 function continueToSetLineNumberHeights() {
 var rowsInThisPass = 0;
 var numRow = 1;
 var srcRow = 1;
 while (numRow && srcRow && rowsInThisPass < MAX_ROWS_PER_PASS) {
 numRow = rowGenerator(numsGenState);
 srcRow = rowGenerator(srcGenState);
 rowsInThisPass++;
 if (numRow && srcRow) {
 if (numRow.offsetHeight != srcRow.offsetHeight) {
 numRow.firstChild.style.height = srcRow.offsetHeight + 'px';
 }
 }
 }
 if (rowsInThisPass >= MAX_ROWS_PER_PASS) {
 setTimeout(continueToSetLineNumberHeights, 10);
 } else {
 alignerRunning = false;
 if (startOver) {
 startOver = false;
 setTimeout(setLineNumberHeights, 500);
 }
 }
 }
 function initLineNumberHeights() {
 // Do 2 complete passes, because there can be races
 // between this code and prettify.
 startOver = true;
 setTimeout(setLineNumberHeights, 250);
 window.onresize = setLineNumberHeights;
 }
 initLineNumberHeights();
</script>

 
 
 <div id="log">
 <div style="text-align:right">
 <a class="ifCollapse" href="#" onclick="_toggleMeta(this); return false">Show details</a>
 <a class="ifExpand" href="#" onclick="_toggleMeta(this); return false">Hide details</a>
 </div>
 <div class="ifExpand">
 
 
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="changelog">
 <p>Change log</p>
 <div>
 <a href="/p/vooga-compsci308-fall2012/source/detail?spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5&amp;r=ddb3de6a9b0a82883032fba336cc854396f52e05">ddb3de6a9b0a</a>
 by vo.zavidovych@gmail.com &lt;vo.zavidovych@gmail.com&gt;
 on Dec 3 (4 days ago)
 &nbsp; <a href="/p/vooga-compsci308-fall2012/source/diff?spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5&r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;format=side&amp;path=/src/vooga/turnbased/Start.java&amp;old_path=/src/vooga/turnbased/Start.java&amp;old=">Diff</a>
 </div>
 <pre>turnbased RPG interfaces with arcade
</pre>
 </div>
 
 
 
 
 
 
 <script type="text/javascript">
 var detail_url = '/p/vooga-compsci308-fall2012/source/detail?r=ddb3de6a9b0a82883032fba336cc854396f52e05&spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5';
 var publish_url = '/p/vooga-compsci308-fall2012/source/detail?r=ddb3de6a9b0a82883032fba336cc854396f52e05&spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5#publish';
 // describe the paths of this revision in javascript.
 var changed_paths = [];
 var changed_urls = [];
 
 changed_paths.push('/src/arcade/database/user/zavidovych.xml');
 changed_urls.push('/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/user/zavidovych.xml?r\x3dddb3de6a9b0a82883032fba336cc854396f52e05\x26spec\x3dsvn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 
 changed_paths.push('/src/arcade/database/userGame/zavidovych.xml');
 changed_urls.push('/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/userGame/zavidovych.xml?r\x3dddb3de6a9b0a82883032fba336cc854396f52e05\x26spec\x3dsvn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 
 changed_paths.push('/src/arcade/database/userMessage/zavidovych.xml');
 changed_urls.push('/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/userMessage/zavidovych.xml?r\x3dddb3de6a9b0a82883032fba336cc854396f52e05\x26spec\x3dsvn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 
 changed_paths.push('/src/vooga/turnbased/Main.java');
 changed_urls.push('/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/Main.java?r\x3dddb3de6a9b0a82883032fba336cc854396f52e05\x26spec\x3dsvn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 
 changed_paths.push('/src/vooga/turnbased/Start.java');
 changed_urls.push('/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/Start.java?r\x3dddb3de6a9b0a82883032fba336cc854396f52e05\x26spec\x3dsvn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 var selected_path = '/src/vooga/turnbased/Start.java';
 
 
 function getCurrentPageIndex() {
 for (var i = 0; i < changed_paths.length; i++) {
 if (selected_path == changed_paths[i]) {
 return i;
 }
 }
 }
 function getNextPage() {
 var i = getCurrentPageIndex();
 if (i < changed_paths.length - 1) {
 return changed_urls[i + 1];
 }
 return null;
 }
 function getPreviousPage() {
 var i = getCurrentPageIndex();
 if (i > 0) {
 return changed_urls[i - 1];
 }
 return null;
 }
 function gotoNextPage() {
 var page = getNextPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoPreviousPage() {
 var page = getPreviousPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoDetailPage() {
 window.location = detail_url;
 }
 function gotoPublishPage() {
 window.location = publish_url;
 }
</script>

 
 <style type="text/css">
 #review_nav {
 border-top: 3px solid white;
 padding-top: 6px;
 margin-top: 1em;
 }
 #review_nav td {
 vertical-align: middle;
 }
 #review_nav select {
 margin: .5em 0;
 }
 </style>
 <div id="review_nav">
 <table><tr><td>Go to:&nbsp;</td><td>
 <select name="files_in_rev" onchange="window.location=this.value">
 
 <option value="/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/user/zavidovych.xml?r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5"
 
 >...ade/database/user/zavidovych.xml</option>
 
 <option value="/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/userGame/zavidovych.xml?r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5"
 
 >...database/userGame/zavidovych.xml</option>
 
 <option value="/p/vooga-compsci308-fall2012/source/browse/src/arcade/database/userMessage/zavidovych.xml?r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5"
 
 >...abase/userMessage/zavidovych.xml</option>
 
 <option value="/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/Main.java?r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5"
 
 >/src/vooga/turnbased/Main.java</option>
 
 <option value="/p/vooga-compsci308-fall2012/source/browse/src/vooga/turnbased/Start.java?r=ddb3de6a9b0a82883032fba336cc854396f52e05&amp;spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5"
 selected="selected"
 >/src/vooga/turnbased/Start.java</option>
 
 </select>
 </td></tr></table>
 
 
 <div id="review_instr" class="closed">
 <a class="ifOpened" href="/p/vooga-compsci308-fall2012/source/detail?r=ddb3de6a9b0a82883032fba336cc854396f52e05&spec=svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5#publish">Publish your comments</a>
 <div class="ifClosed">Double click a line to add a comment</div>
 </div>
 
 </div>
 
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="older_bubble">
 <p>Older revisions</p>
 
 <a href="/p/vooga-compsci308-fall2012/source/list?path=/src/vooga/turnbased/Start.java&r=ddb3de6a9b0a82883032fba336cc854396f52e05">All revisions of this file</a>
 </div>
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="fileinfo_bubble">
 <p>File info</p>
 
 <div>Size: 1065 bytes,
 42 lines</div>
 
 <div><a href="//vooga-compsci308-fall2012.googlecode.com/git/src/vooga/turnbased/Start.java">View raw file</a></div>
 </div>
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 </div>
 </div>


</div>

</div>
</div>

<script src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/prettify/prettify.js"></script>
<script type="text/javascript">prettyPrint();</script>


<script src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/source_file_scripts.js"></script>

 <script type="text/javascript" src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/kibbles.js"></script>
 <script type="text/javascript">
 var lastStop = null;
 var initialized = false;
 
 function updateCursor(next, prev) {
 if (prev && prev.element) {
 prev.element.className = 'cursor_stop cursor_hidden';
 }
 if (next && next.element) {
 next.element.className = 'cursor_stop cursor';
 lastStop = next.index;
 }
 }
 
 function pubRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initialized) {
 reloadCursors();
 }
 }
 
 function draftRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initialized) {
 reloadCursors();
 }
 }
 
 function draftDestroyed(data) {
 updateCursorForCell(data.cellId, 'nocursor');
 if (initialized) {
 reloadCursors();
 }
 }
 function reloadCursors() {
 kibbles.skipper.reset();
 loadCursors();
 if (lastStop != null) {
 kibbles.skipper.setCurrentStop(lastStop);
 }
 }
 // possibly the simplest way to insert any newly added comments
 // is to update the class of the corresponding cursor row,
 // then refresh the entire list of rows.
 function updateCursorForCell(cellId, className) {
 var cell = document.getElementById(cellId);
 // we have to go two rows back to find the cursor location
 var row = getPreviousElement(cell.parentNode);
 row.className = className;
 }
 // returns the previous element, ignores text nodes.
 function getPreviousElement(e) {
 var element = e.previousSibling;
 if (element.nodeType == 3) {
 element = element.previousSibling;
 }
 if (element && element.tagName) {
 return element;
 }
 }
 function loadCursors() {
 // register our elements with skipper
 var elements = CR_getElements('*', 'cursor_stop');
 var len = elements.length;
 for (var i = 0; i < len; i++) {
 var element = elements[i]; 
 element.className = 'cursor_stop cursor_hidden';
 kibbles.skipper.append(element);
 }
 }
 function toggleComments() {
 CR_toggleCommentDisplay();
 reloadCursors();
 }
 function keysOnLoadHandler() {
 // setup skipper
 kibbles.skipper.addStopListener(
 kibbles.skipper.LISTENER_TYPE.PRE, updateCursor);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_top', 50);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_bottom', 100);
 // Register our keys
 kibbles.skipper.addFwdKey("n");
 kibbles.skipper.addRevKey("p");
 kibbles.keys.addKeyPressListener(
 'u', function() { window.location = detail_url; });
 kibbles.keys.addKeyPressListener(
 'r', function() { window.location = detail_url + '#publish'; });
 
 kibbles.keys.addKeyPressListener('j', gotoNextPage);
 kibbles.keys.addKeyPressListener('k', gotoPreviousPage);
 
 
 kibbles.keys.addKeyPressListener('h', toggleComments);
 
 }
 </script>
<script src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/code_review_scripts.js"></script>
<script type="text/javascript">
 function showPublishInstructions() {
 var element = document.getElementById('review_instr');
 if (element) {
 element.className = 'opened';
 }
 }
 var codereviews;
 function revsOnLoadHandler() {
 // register our source container with the commenting code
 var paths = {'svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5': '/src/vooga/turnbased/Start.java'}
 codereviews = CR_controller.setup(
 {"loggedInUserEmail":"zy26@duke.edu","relativeBaseUrl":"","projectHomeUrl":"/p/vooga-compsci308-fall2012","assetVersionPath":"http://www.gstatic.com/codesite/ph/7707091683928850954","assetHostPath":"http://www.gstatic.com/codesite/ph","domainName":null,"projectName":"vooga-compsci308-fall2012","token":"tcpwL7AGS4MLuCtpbyV-QdgUqPs:1354928002761","profileUrl":"/u/zy26@duke.edu/"}, '', 'svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5', paths,
 CR_BrowseIntegrationFactory);
 
 // register our source container with the commenting code
 // in this case we're registering the container and the revison
 // associated with the contianer which may be the primary revision
 // or may be a previous revision against which the primary revision
 // of the file is being compared.
 codereviews.registerSourceContainer(document.getElementById('lines'), 'svn5f7f3bbd57a8e3ac71a7ef3238cd98e1341d55c5');
 
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_DRAFT_PLATE, showPublishInstructions);
 
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_PUB_PLATE, pubRevealed);
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_DRAFT_PLATE, draftRevealed);
 codereviews.registerActivityListener(CR_ActivityType.DISCARD_DRAFT_COMMENT, draftDestroyed);
 
 
 
 
 
 
 
 var initialized = true;
 reloadCursors();
 }
 window.onload = function() {keysOnLoadHandler(); revsOnLoadHandler();};

</script>
<script type="text/javascript" src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/dit_scripts.js"></script>

 
 
 
 <script type="text/javascript" src="http://www.gstatic.com/codesite/ph/7707091683928850954/js/ph_core.js"></script>
 
 
 
 
</div> 

<div id="footer" dir="ltr">
 <div class="text">
 <a href="/projecthosting/terms.html">Terms</a> -
 <a href="http://www.google.com/privacy.html">Privacy</a> -
 <a href="/p/support/">Project Hosting Help</a>
 </div>
</div>
 <div class="hostedBy" style="margin-top: -20px;">
 <span style="vertical-align: top;">Powered by <a href="http://code.google.com/projecthosting/">Google Project Hosting</a></span>
 </div>

 
 


 
 </body>
</html>

