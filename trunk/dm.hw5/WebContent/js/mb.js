function newMessage() {
	setText(gel('newMessageError'), '');
	setEl(gel('main'), gel('newMessage'));
}

function postMessage(target) {
    if (!target) return;

	var tl = target.title.value;     
    var tp = target.type.value; 
    var rn = target.rank.value;
    var bd = target.body.value;
    
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
    
    request2(
    	'newmessage',
    	'title='    + escape(tl) + '&' +
    	'type='     + escape(tp) + '&' +
    	'rank='     + escape(rn) + '&' +
    	formDate(target, "s") + '&' +
    	formDate(target, "e") + '&' +
    	'body='     + escape(bd)    	,
    	'POST', err, function (xml) {
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

function sharedSearch() {
	setText(gel('sharedSearchError'), '');
	setEl(gel('main'), gel('sharedSearch'));
}

// BOARD VIEW BEGIN
function boardView() {
	showMessages('boardView', 'boardview', null, 'GET');
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
	//gel('boardViewFrame').contentDocument = html;
	setEl(gel('main'), gel(target));
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
    var rn = target.rank.value;
    var fa = target.fullAccess.value;
    
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
    
    request2(
    	'register',
    	'username=' + escape(un) + '&' +
    	'password=' + escape(pw) + '&' +
    	'type='     + escape(tp) + '&' +
    	'rank='     + escape(rn) + '&' +
    	'full_access=' + escape(fa)    	,
    	'POST', err, function (xml) {
			setEl(gel('main'), gel('reg_ok'));
    	});

    return false;
}
// NEW USER END

// LOGIN/LOGOUT SECTION BEGIN
function loginUpdate() {
	var id = username ? 'loggedin' : 'login';
	setText(gel("user"), username ? username : "guest");
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
    
    var username = target.username.value;
    var password = target.password.value;
    
    request2(
    	'login',
    	'username=' + escape(username) + '&' +
    	'password=' + escape(password),
    	'POST', gel('loginError'), function (xml) {
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
    /* Set up the request */
    var xmlhttp =  new XMLHttpRequest();
    xmlhttp.open(method, url, true);
    
    /* The callback function */
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4)
            if (xmlhttp.status == 200)
            	callback(xmlhttp.responseXML);
    }
    
    /* Send the POST request */
    if (params) {
    	xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    	xmlhttp.send(params);
    } else 
    	xmlhttp.send(null);
}

function request2(url, params, method, errEl, callback) {
	request(url, params, method, function (xml) {
    		if (getResult(xml) == "true")
				callback(xml);
			else
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