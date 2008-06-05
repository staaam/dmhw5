//----------------------------------------------------------
// Minimal Ajax request object.
//----------------------------------------------------------
function Ajax (url, parms, method, callback) {

    this.url = url;
    this.parms = parms;
    this.method = method;
    this.callback = callback;
    this.async = true;

    this.create ();

    this.req.onreadystatechange = this.dispatch (this);
}

//----------------------------------------------------------
Ajax.prototype.dispatch = function (ajax) {

    function funcRef()
    {
	if (ajax.req.readyState == 4) {
	    if (ajax.callback) {
		ajax.callback (ajax.req);
	    }
	}
    }

    return funcRef;
}

//----------------------------------------------------------
Ajax.prototype.request = function () {

    if (this.method == "POST") {
	this.req.open("POST", this.url, this.async);
	this.req.send (this.parms);
    }
    else if (this.method == "GET") {
	this.req.open("GET", this.url + this.parms, this.async);
	this.req.send (null);
    }

}

//----------------------------------------------------------
Ajax.prototype.setAsync = function (async) {

    this.async = async;
}

//----------------------------------------------------------
Ajax.prototype.create = function () {

    var xmlhttp;
    /*@cc_on
    @if (@_jscript_version >= 5)
 
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } 
    catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        catch (E) {
            xmlhttp = false;
        }
    }

    @else

    xmlhttp = false;

    @end @*/

    if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
	try {
	    xmlhttp = new XMLHttpRequest();
	} catch (e) {
	    xmlhttp = false;
	}
    }

    this.req = xmlhttp;
}
