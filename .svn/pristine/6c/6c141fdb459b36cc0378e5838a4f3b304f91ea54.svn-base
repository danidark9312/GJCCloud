function formatoFecha(fecha)
{
//	Formato nuevo, para que salga en formato dd/MM/yyyy
   var fechaReturn = "";
	dia = (fecha.getDate()).toString();
	
	if (dia.toString().length < 2) {
		fechaReturn = fechaReturn.concat("0")
	}
	fechaReturn = fechaReturn.concat(dia);
	fechaReturn = fechaReturn.concat("-");
	
	mes = fecha.getMonth() + 1;
	if (mes.toString().length < 2) {
		fechaReturn = fechaReturn.concat("0");
	}
	fechaReturn = fechaReturn.concat(mes);
	
	ano = fecha.getFullYear();
	fechaReturn = fechaReturn.concat("-");
	fechaReturn = fechaReturn.concat(ano);
	return fechaReturn;
}

function formatoFechaAnio(fecha){
//	Formato nuevo, para que salga en formato yyyy/MM/dd
	var fechaReturnForamoAnios;
	ano = (fecha.getFullYear()).toString();
	fechaReturnForamoAnios = ano.concat("-");
	mes = fecha.getMonth() + 1;
	if (mes.toString().length < 2) {
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat("0");
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat(mes);
	}
	else {
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat(mes);
	}
	dia = fecha.getDate();
	fechaReturnForamoAnios = fechaReturnForamoAnios.concat("-");
	if (dia.toString().length < 2) {
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat("0");
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat(dia);
	}
	else {
		fechaReturnForamoAnios = fechaReturnForamoAnios.concat(dia);
	}
	return fechaReturnForamoAnios;
}

var contenido_textarea = "";

function soloLetras(e){
	
	if(e.which == 8364 || e.which == 186){ 
		return false;
	}
    tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla==8) return true; // 3
    te = String.fromCharCode(tecla); // 5
    patron =/[\>\<^#$%&!")1234567890(º=?¡¿'·^*+`ç+¨:.,\Ç_}\/ª\\|@~€¬{\]\[-]/;// 4
    return !patron.test(te); // 6
}

function soloNumeros(e){
	//Este metodo no permite copiar con el teclado numerico. No usar
	var soloNumerosDeprecado = function(e){
		key = e.keyCode || e.which;
	    tecla = String.fromCharCode(key).toLowerCase();
	    numeros = "0123456789";
	    especiales = [8,9];
	    tecla_especial = false
	    for(var i in especiales){
	    	if(key == especiales[i]){
	    		tecla_especial = true;
	    		break;
	        } 
	    }
	    if(numeros.indexOf(tecla)==-1 && !tecla_especial)
	        return false;
	};
	//Metodo correcto actual
	if ($.inArray(e.keyCode, [46, 8, 9, 27, 13]) !== -1 ||
        (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) || 
        (e.keyCode >= 35 && e.keyCode <= 40)) {
             // let it happen, don't do anything
             return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }    
}

function decimales(evt,control){
    //Limita un control a números con dos decimales.
    var texto = control.value;
    var coma = texto.indexOf('.');
     
    if (evt.keyCode <= 13 || (evt.keyCode >= 48 && evt.keyCode <= 57)){
        if ((coma!=-1) && (texto.length - (coma + 1))>=2){
            return false;
        }
    }
    else if (evt.keyCode == 46 && texto.length>=1 ){
        if (coma!=-1 && texto.indexOf('.',coma)!=-1 ){
            return false;
        }
    }else{
        return false;
    }
    return true;
}

function soloLetrasNumeros(e){
	//console.log("e.keyCode: "+e.keyCode+" e.which: "+e.which);
	if(e.which == 8364 || e.which == 186 || e.which == 172 || e.which == 170 || e.which == 33 
			|| e.which == 34 || e.which == 183	|| e.which == 63 || e.which == 191 || e.which == 168
			|| e.which == 161 || e.which == 94 || e.which == 199 || e.which == 231){
		return false;
	}
	tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla==8) return true; // 3
    te = String.fromCharCode(tecla); // 5
    patron =/[\>\<^#$%&!")(=?¡¿'·^*+`ç+.,¨:\Ç_}\/ª\\|@~\€\¬{\]\[-]/;// 4
    return !patron.test(te); 
}

function soloLetrasNumerosE(e){
	tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla==8) return true; // 3
    te = String.fromCharCode(tecla); // 5
    patron =/[\>\<^#$%&!")(=?¡¿\ '·^*+`ç+¨:.,º\Ç_}\/ª\\|@~€¬{\]\[-]/;// 4
    return !patron.test(te); // 6
}

function validarCaracteres() {
	if(
		event.keyCode == 38 || //Flecha hacia arriba
		event.keyCode == 34	|| //"
		//event.keyCode == 44	|| //,
		event.keyCode == 45	|| //-
		//event.keyCode == 59	|| //;
		event.keyCode == 58	|| //;
		event.keyCode == 60	|| //<
		event.keyCode == 62	|| //>
		(event.keyCode > 32 && event.keyCode < 37) ||
		(event.keyCode > 39 && event.keyCode < 44) ||
		(event.keyCode > 46 && event.keyCode < 48) ||
		(event.keyCode > 59 && event.keyCode < 65) ||
		(event.keyCode > 90 && event.keyCode < 96) ||
		(event.keyCode > 122 && event.keyCode < 175) ||
		(event.keyCode > 175 && event.keyCode < 184) ||
		(event.keyCode > 184 && event.keyCode < 191) ||
		(event.keyCode > 191)
	)
	    event.returnValue = false ;
    else
           event.returnValue = true ;     
}

function soloDirecciones(e){
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz0123456789#º-.,";
    especiales = [8,46,9];
    tecla_especial = false
    for(var i in especiales){
    	if(key == especiales[i]){
    		tecla_especial = true; 
    		break;
        } 
    }
    if(letras.indexOf(tecla)==-1 && !tecla_especial)
        return false;
}

function soloFechas(e){
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0123456789/-";
    especiales = [8,9];
    tecla_especial = false
    for(var i in especiales){
    	if(key == especiales[i]){
    		tecla_especial = true;
    		break;
        } 
    }
    if(letras.indexOf(tecla)==-1 && !tecla_especial)
        return false;
}

function EstaVacio(Dato){
	if(Dato!=null){
		for (var i=0;i<Dato.length;i++){
		    if (Dato.substring(i,i+1)!= " ")
		    	return(false);
		    }
	}
	  
	  	return(true);
	}

function imposeMaxLength(Event, Object, MaxLen)
{
	
	tecla = (document.all) ? Event.keyCode : Event.which; // 2
    if (tecla==8) return true; // 3
    te = String.fromCharCode(tecla); // 5
    patron =/[\>\<^#$%&!")(=?¡¿'·^*+`ç+¨:\Ç_}º\/ª\\|@~€¬{\]\[]/;// 4
    var caracter = !patron.test(te); // 6
    
	var len =  (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40));
    return  (caracter && len);
}

function reescalarModal(idModal){
    var size = {width: $(window).width() , height: $(window).height() }
    var offset = 20;
    var offsetBody = 150;
    
    $('#' + idModal).css('height', size.height - offset );
    $('.modal-body').css('height', size.height - (offset + offsetBody));
    $('#' + idModal).css('top', 0);
    $(window).bind('resize', reescalarModal);
}


contenido_textarea = "" ;
function valida_longitud(d, longitud ){
		  var num_caracteres = d.value.length; 
			
		   if (num_caracteres >= longitud){ 
		      d.value = contenido_textarea;
		   }else{
		      contenido_textarea = d.value;
		   } 
	 
	}

	function cuenta(texto){
	   document.forms[0].caracteres.value=document.forms[0].texto.value.length;
	}
	
function validarTextArea(e,cont){
	key = e.keyCode || e.which;
	especiales = [8,9];
	tecla_especial = false;
	for(var i in especiales){
    	if(key == especiales[i]){
    		tecla_especial = true;
    		break;
        } 
    }
	var tamannoCadena = $('#condicionesPago').val(); 
	if((tamannoCadena.length+1) <= cont){
		return true;
	}else if(tecla_especial){
		if((tamannoCadena.length) == cont){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}


//jQuery.fn.reset = function () {
//	  $(this).each (function() { this.reset(); });
//};

function getParameter(parameter){
	// Obtiene la cadena completa de URL
	var url = location.href;
	/* Obtiene la posicion donde se encuentra el signo ?, 
	ahi es donde empiezan los parametros */
	var index = url.indexOf("?");
	/* Obtiene la posicion donde termina el nombre del parametro
	e inicia el signo = */
	index = url.indexOf(parameter,index) + parameter.length;
	/* Verifica que efectivamente el valor en la posicion actual 
	es el signo = */ 
	if (url.charAt(index) == "="){
	// Obtiene el valor del parametro
	var result = url.indexOf("&",index);
	
	if (result == -1){result=url.length;};
	 return  url.substring(index + 1,result);

	}
	return null;
} 


function validarCamposDouble( event, campo, enteros, decimales ) {
	

    if (event.shiftKey==1||event.ctrlKey||event.altKey){
        return false;
    } else {
        if ( event.keyCode != 8 && event.keyCode != 37 && event.keyCode != 39 ) {
            if ((event.keyCode > 45 && event.keyCode < 59) || (event.keyCode>36 && event.keyCode<41) || (event.keyCode==8) || (event.keyCode > 95 && event.keyCode < 106) || event.keyCode==9 || ( (event.keyCode == 110 || event.keyCode == 190) && campo.value.indexOf( "." ) == -1 ) ) {
                if ( campo.value.length == ( parseInt( enteros ) ) ) {
                    if ( ( event.keyCode == 110 || event.keyCode == 190 ) || campo.value.indexOf( "." ) != -1 ) {
                        if( campo.value.indexOf( "." ) > 8 && campo.value.indexOf( "." ) < 11 ){
                            return true;	
                        }
                    } else {
                        return false;
                    }
                } else {
                    if ( campo.value.length >= parseInt( enteros ) ) {
                        if ( campo.value.indexOf( "." ) != -1 ) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((event.keyCode == 110 || event.keyCode == 190) && campo.value.length == 0 ){
                            return false
                        }else{
                            return true;
                        }	
                    }
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}

function validar_email(valor)
{
    // creamos nuestra regla con expresiones regulares.
    var filter = /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;
    // utilizamos test para comprobar si el parametro valor cumple la regla
    if(filter.test(valor)){
    	return true;
    }else{
    	return false;
    }
        
}

function validarPorcentaje(dom,max){
	var val = dom.value;
	
	if(val>max){
	dom.value = 100;
	}
	
}

var formatNumber = function(num) {
	if(num&&num!=""){
		var array = num.toString().split('');
		var index = -3;
		while (array.length + index > 0) {
			array.splice(index, 0, '.');
			index -= 4;
		}
		return array.join('');
	}else{
		return num;
	}
};

function replaceAll(str,find, replace) 
{
  while( str.indexOf(find) > -1)
  {
    str = str.replace(find, replace);
  }
  return str;
}

function FormatDate() {

	var dateFormat = function () {
	    var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
	        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
	        timezoneClip = /[^-+\dA-Z]/g,
	        pad = function (val, len) {
	            val = String(val);
	            len = len || 2;
	            while (val.length < len) val = "0" + val;
	            return val;
	        };

	    // Regexes and supporting functions are cached through closure
	    return function (date, mask, utc) {
	        var dF = dateFormat;

	        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
	        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
	            mask = date;
	            date = undefined;
	        }

	        // Passing date through Date applies Date.parse, if necessary
	        date = date ? new Date(date) : new Date;
	        if (isNaN(date)) throw SyntaxError("invalid date");

	        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

	        // Allow setting the utc argument via the mask
	        if (mask.slice(0, 4) == "UTC:") {
	            mask = mask.slice(4);
	            utc = true;
	        }

	        var _ = utc ? "getUTC" : "get",
	            d = date[_ + "Date"](),
	            D = date[_ + "Day"](),
	            m = date[_ + "Month"](),
	            y = date[_ + "FullYear"](),
	            H = date[_ + "Hours"](),
	            M = date[_ + "Minutes"](),
	            s = date[_ + "Seconds"](),
	            L = date[_ + "Milliseconds"](),
	            o = utc ? 0 : date.getTimezoneOffset(),
	            flags = {
	                d:    d,
	                dd:   pad(d),
	                ddd:  dF.i18n.dayNames[D],
	                dddd: dF.i18n.dayNames[D + 7],
	                m:    m + 1,
	                mm:   pad(m + 1),
	                mmm:  dF.i18n.monthNames[m],
	                mmmm: dF.i18n.monthNames[m + 12],
	                yy:   String(y).slice(2),
	                yyyy: y,
	                h:    H % 12 || 12,
	                hh:   pad(H % 12 || 12),
	                H:    H,
	                HH:   pad(H),
	                M:    M,
	                MM:   pad(M),
	                s:    s,
	                ss:   pad(s),
	                l:    pad(L, 3),
	                L:    pad(L > 99 ? Math.round(L / 10) : L),
	                t:    H < 12 ? "a"  : "p",
	                tt:   H < 12 ? "am" : "pm",
	                T:    H < 12 ? "A"  : "P",
	                TT:   H < 12 ? "AM" : "PM",
	                Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
	                o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
	                S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
	            };

	        return mask.replace(token, function ($0) {
	            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
	        });
	    };
	}();

	// Some common format strings
	dateFormat.masks = {
	    "default":      "ddd mmm dd yyyy HH:MM:ss",
	    shortDate:      "m/d/yy",
	    mediumDate:     "mmm d, yyyy",
	    longDate:       "mmmm d, yyyy",
	    fullDate:       "dddd, mmmm d, yyyy",
	    shortTime:      "h:MM TT",
	    mediumTime:     "h:MM:ss TT",
	    longTime:       "h:MM:ss TT Z",
	    isoDate:        "yyyy-mm-dd",
	    isoTime:        "HH:MM:ss",
	    isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
	};

	// Internationalization strings
	dateFormat.i18n = {
	    dayNames: [
	        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
	        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	    ],
	    monthNames: [
	        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
	        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	    ]
	};

	// For convenience...
	Date.prototype.format = function (mask, utc) {
	    return dateFormat(this, mask, utc);
	};
}


function quitarSeparadoresMiles(numero, separadorMiles) {
	while(numero.indexOf(separadorMiles)!=-1){
		numero = numero.replace(separadorMiles,"");
	}
	return numero;
}


function replaceAll( text, busca, reemplaza ){
  while (text.toString().indexOf(busca) != -1)
      text = text.toString().replace(busca,reemplaza);
  return text;
}


/** METODO PARA SEPARADORES DE MIL */
function format(input) 
{
var num = input.value.replace(/\./g,'');
if(!isNaN(num)){
num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1.');
num = num.split('').reverse().join('').replace(/^[\.]/,'');
input.value = num;
}
 
else{ alert('Solo se permiten numeros');
input.value = input.value.replace(/[^\d\.]*/g,'');
}
}

function directorioActual(raiz, directorio){
	var html="";
	html+='<ol class="breadcrumb">';
	html+='<li>';
	html+='    <a href="visorCaso">Inicio</a>';
	html+='</li>';
	html+='<li>';
	html+='    '+raiz;
	html+='</li>';
	html+='<li class="active">';
	html+='    <strong>'+directorio+'</strong>';
	html+='</li>'
	html+='</ol>';
	
	$("#directorioActual").html(html);
}


function fnExcelReport(tablaExportar, imgColSpan, titulo, tituloColSpan) {
    var tab_text="<table border='2px'>";
    var urlImagen = window.location.origin + context + "/img/logo-login.png";
    tab_text += "<tr><td height='156' colspan=" + imgColSpan + "><img src=" + urlImagen + "></img></td><td colspan = " + tituloColSpan + "><p style='font-size:30px'>" + titulo + "</p></td></tr>";
    tab_text += "<tr bgcolor='#CCCCCC'>"
    var textRange; var j=0;
    tab = document.getElementById(tablaExportar); // id of table
    
    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
        tab_text = tab_text + tab.rows[j].innerHTML + "</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    //tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // removes input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,"Say Thanks to Sumit.xls");
    }  
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel;base64,' + $.base64.encode(tab_text));
    $(tab).remove();
    return (sa);
}



