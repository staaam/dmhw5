function newMessage() {
	setText(gel('newMessageError'), '');
	setEl(gel('main'), gel('newMessage'));
}

function login() {
	setText(gel('loginError'), '');
	setEl(gel('main'), gel('login'));
}

function administer() {
	setText(gel('adminStatus'), '');
	setEl(gel('main'), gel('admin'));
}

function adminAct(act) {
	if (!act) return;
	request2('admin', 'action='+act, 'GET', gel('adminStatus'));
}

function postMessage(target) {
    if (!target) return;

    var tp = target.type.value; 
    
    var err = gel('newMessageError');
    setText(err, '');
    if (!tp) {
    	setText(err, 'Type should not be empty');
    	return false;
    }
    if (tp.length > 15) {
    	setText(err, 'Type too long. Should be no more than 15 characters');
    	return false;
    }
    
    request2('newmessage', allElements(target), 'POST', err, function (xml) {
			setEl(gel('main'), gel('post_ok'));
    	});

    return false;
}
 
function formDate(form, p) {
	var e = [ "y", "m", "d", "H", "M" ];
	var s = "";
	for (var i=0; i<e.length; i++) {
		var el = p + e[i];
		if (s) s += "&";
		s += el +"=" + escape(form[el].value);
	}
	return s;
} 

function localSearch() {
	setText(gel('localSearchError'), '');
	setEl(gel('main'), gel('localSearch'));
}

function doLocalSearch(target) {
    if (!target) return;
	showMessages('search', 'localsearch', allElements(target), 'POST');
    return false;
}

function sharedSearch() {
	var err = gel('sharedSearchError');
	setText(err, '');
	gel('endpointsList').innerHTML = "";
	request2('sharedsearch','getendpoints=1','GET', err, function (xml) {
		var epl = gel('endpointsList');
		var els = xml.getElementsByTagName('endpoint');
		var html = "";
		for (var i=0; i<els.length; i++) {
			var el = els[i];
			var ep = el.firstChild.data;
			html += '<input type="checkbox" name="endpoints" value="'+ep+'" checked="checked"/>'+ep+'<br/>';
		}
		gel('endpointsList').innerHTML = html;		
	});
	setEl(gel('main'), gel('sharedSearch'));
}

function doSharedSearch(target) {
    if (!target) return;
	showMessages('search', 'sharedsearch', allElements(target), 'POST');
    return false;
}

function allElements(form) {
	var s = '';
	var elem = form.elements;
	for(var i = 0; i < elem.length; i++) {
		if (s) s += "&";
		
		var e = elem[i];
		if (e.type != 'checkbox' || e.checked)
			s += escape(elem[i].name) + '=' + escape(elem[i].value);
	}
	return s; 
}

function showPrefs() {
	setEl(gel('main'), gel('prefs'));
}

function setPrefs(target) {
	if (!target) return;
	
	AIM.submit(target, {
		'onStart' : function () {
			setText(get('uploadStatus'), 'Uploading...');
		},
		'onComplete' : function (response) {
			setText(get('uploadStatus'), 'Done');
		}
	});	
}

function toggle(id) {
	var s = gel(id).style;
	s.display = s.display == 'none' ? 'block' : 'none';
}

// BOARD VIEW BEGIN
function boardView() {
	showMessages('board', 'boardview', null, 'GET');
}

function showMessages(target, url, params, method) {
	var xslt;
	var xml;
	request(url, params, method, function (x) {
		xml = x;
		showBoard(target, xml, xslt);
	});
	request('messages.xsl', null, 'GET', function (x) {
		xslt = x;
		showBoard(target, xml, xslt);
	});
}

function showBoard(target, xml, xslt) {
	if (!xml || !xslt) return;
	var html = xsltProcess(xml, xslt);
	gel(target).innerHTML = html;
//	setEl(gel('main'), gel(target));
}
// BOARD VIEW END

// NEW USER BEGIN
function register() {
	setEl(gel('main'), gel('newuser')); 
}

function doRegister(target) {
    if (!target) return;
    
    var un = target.username.value; 
    var pw = target.password.value; 
    var pw2 = target.confirm.value; 
    var tp = target.type.value; 
    
    var err = gel('newuserError');
    setText(err, '');
    if (!un) {
    	setText(err, 'Username should not be empty');
    	return false;
    }
    if (!pw) {
    	setText(err, 'Password should not be empty');
    	return false;
    }
    if (!tp) {
    	setText(err, 'Type should not be empty');
    	return false;
    }

    if (pw != pw2) {
    	setText(err, 'Passwords don\'t match');
    	return false;
    }
    if (tp.length > 15) {
    	setText(err, 'Type too long. Should be no more than 15 characters');
    	return false;
    }
    
    request2('register', allElements(target), 'POST', err, function (xml) {
			setEl(gel('main'), gel('reg_ok'));
    	});

    return false;
}
// NEW USER END

// LOGIN/LOGOUT SECTION BEGIN
function loginUpdate() {
	var id = 'logged' + (username ? 'in' : 'out');
	setText(gel("user"), username ? username : "guest");
	gel('prefs_menu').style.display = username ? 'block' : 'none';
	setEl(gel('login_status'), gel(id));
	setText(gel('loginError'), "");
	boardView();
}

function doLogout() {
	request('logout', null, 'GET', function() {
		setLogin("false");
	});
}

function doLogin(target) {
    if (!target) return;
    
    request2('login', allElements(target), 'POST', gel('loginError'),
    	function (xml) {
			setLogin("true", username);
    	});

    return false;
}

function setLogin(success, un) {
		
	username = success == "true" ? un : "";
	loginUpdate();
}
// LOGIN/LOGOUT SECTION END
function request(url, params, method, callback) {
	if (params && method == 'GET') {
		url = url + '?' + params;
		params = null;
	}

    /* Set up the request */
    var xmlhttp =  new XMLHttpRequest();
    xmlhttp.open(method, url, true);
    
    /* The callback function */
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if (xmlhttp.status == 200)
            	callback(xmlhttp.responseXML);
        }
    }
    
    if (params && method == 'POST') {
	    /* Send the POST request */
	   	xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	   	xmlhttp.send(params);
    } else 
    	xmlhttp.send(null);
}

function request2(url, params, method, errEl, callback) {
	request(url, params, method, function (xml) {
    		if (getResult(xml) == "true") {
    			if (callback)
					callback(xml);
				else 
					setText(errEl, "Success"); 
			} else
				setText(errEl, getMsg(xml));				
    	});    
}

function setText(sp, text) {
	if (!sp) return;
	if (!(sp.childNodes && sp.childNodes[0]))
		sp.appendChild(document.createElement('div'));

	var newText = document.createTextNode(text);
	var ch = sp.childNodes;
	if (ch)	ch = ch[0];
	if (ch) sp.replaceChild(newText, ch);
}

function setEl(st, nch) {
	if (!st) return;
	if (!(st.childNodes && st.childNodes[0]))
		st.appendChild(document.createElement('div'));

	var ch = st.childNodes[0];
	if (ch != nch)
		swap(ch, nch);
}

function gel(id) {
	return document.getElementById(id);
}

function swap(el1, el2) {
	var tmp = document.createElement('div');
	var p1 = el1.parentNode;
	var p2 = el2.parentNode;
	p1.replaceChild(tmp, el1);
	p2.replaceChild(el1, el2);
	p1.replaceChild(el2, tmp);
}

function getXmlText(xml, tag) {
	return xml.getElementsByTagName(tag)[0].firstChild.data;
}

function getResult(xml) {
	return getXmlText(xml, 'result');
}

function getMsg(xml, tag) {
	return getXmlText(xml, 'msg');
}

function init() {
	loginUpdate();
}