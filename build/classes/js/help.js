function showMessage(templaateCode,noofLabel){
  // alert("e --->"+templaateCode+"---"+noofLabel);	
   deployQZ();
   useDefaultPrinter();
  // printZPL();
	 
  for(var i=0;i<noofLabel;i++){
   printZPL(templaateCode);
   }
}


/**
* Optionally used to deploy multiple versions of the applet for mixed
* environments.  Oracle uses document.write(), which puts the applet at the
* top of the page, bumping all HTML content down.
*/


/**
* Deploys different versions of the applet depending on Java version.
* Useful for removing warning dialogs for Java 6.  This function is optional
* however, if used, should replace the <applet> method.  Needed to address 
* MANIFEST.MF TrustedLibrary=true discrepency between JRE6 and JRE7.
*/
function deployQZ() {

	
	var attributes = {id: "qz", code:'qz.PrintApplet.class', 
		archive:'http://192.168.102.213:8080/E_LabelAssetMgmt/resources/js/qz-print.jar', width:1, height:1};
	var parameters = {jnlp_href: 'http://192.168.102.213:8080/E_LabelAssetMgmt/resources/js/qz-print_jnlp.jnlp', 
		cache_option:'plugin', disable_logging:'false', 
		initial_focus:'false'};
	if (deployJava.versionCheck("1.7+") == true) {} 

	else if (deployJava.versionCheck("1.6+") == true) {
		attributes['archive'] = 'http://192.168.102.213:8080/E_LabelAssetMgmt/resources/jsqz-print.jar';
		parameters['jnlp_href'] = 'http://192.168.102.213:8080/E_LabelAssetMgmt/resources/js/qz-print_jnlp.jnlp';
	}
	deployJava.runApplet(attributes, parameters, '1.5');
}

/**
* Automatically gets called when applet has loaded.
*/
function qzReady() {
	// Setup our global qz object
	window["qz"] = document.getElementById('qz');
	var title = document.getElementById("title");
	if (qz) {
		try {
			//alert("esvk");
			title.innerHTML = title.innerHTML + " " + qz.getVersion();
			document.getElementById("content").style.background = "#F0F0F0";
		} catch(err) { // LiveConnect error, display a detailed meesage
			document.getElementById("content").style.background = "#F5A9A9";
			alert("ERROR:  \nThe applet did not load correctly.  Communication to the " + 
				"applet has failed, likely caused by Java Security Settings.  \n\n" + 
				"CAUSE:  \nJava 7 update 25 and higher block LiveConnect calls " + 
				"once Oracle has marked that version as outdated, which " + 
				"is likely the cause.  \n\nSOLUTION:  \n  1. Update Java to the latest " + 
				"Java version \n          (or)\n  2. Lower the security " + 
				"settings from the Java Control Panel.");
	  }
  }
}

/**
* Returns whether or not the applet is not ready to print.
* Displays an alert if not ready.
*/
function notReady() {
	// If applet is not loaded, display an error
	if (!isLoaded()) {
		return true;
	}
	// If a printer hasn't been selected, display a message.
	else if (!qz.getPrinter()) {
		alert('Please select a printer first by using the "Detect Printer" button.');
		return true;
	}
	return false;
}

/**
* Returns is the applet is not loaded properly
*/
function isLoaded() {
	if (!qz) {
		alert('Error:\n\n\tPrint plugin is NOT loaded!');
		return false;
	} else {
		try {
			if (!qz.isActive()) {
				alert('Error:\n\n\tPrint plugin is loaded but NOT active!');
				return false;
			}
		} catch (err) {
			alert('Error:\n\n\tPrint plugin is NOT loaded properly!');
			return false;
		}
	}
	return true;
}

/**
* Automatically gets called when "qz.print()" is finished.
*/
function qzDonePrinting() {
	// Alert error, if any
	if (qz.getException()) {
		alert('Error printing:\n\n\t' + qz.getException().getLocalizedMessage());
		qz.clearException();
		return; 
	}
	
	// Alert success message
	alert('Successfully sent print data to "' + qz.getPrinter() + '" queue.');
	 window.location="/E_LabelAssetMgmt/pages/main.jsf";
}

/***************************************************************************
* Prototype function for finding the "default printer" on the system
* Usage:
*    qz.findPrinter();
*    window['qzDoneFinding'] = function() { alert(qz.getPrinter()); };
***************************************************************************/
function useDefaultPrinter() {
	if (isLoaded()) {
		// Searches for default printer
		qz.findPrinter();
		
		// Automatically gets called when "qz.findPrinter()" is finished.
		window['qzDoneFinding'] = function() {
			// Alert the printer name to user
			var printer = qz.getPrinter();
			alert(printer !== null ? 'Default printer found: "' + printer + '"':
				'Default printer ' + 'not found');
			
			// Remove reference to this function
			window['qzDoneFinding'] = null;
		};
	}
}





/***************************************************************************
* Prototype function for finding the closest match to a printer name.
* Usage:
*    qz.findPrinter('zebra');
*    window['qzDoneFinding'] = function() { alert(qz.getPrinter()); };
***************************************************************************/
function findPrinter(name) {
	// Get printer name from input box
	var p = document.getElementById('printer');
	if (name) {
		p.value = name;
	}
	
	if (isLoaded()) {
		// Searches for locally installed printer with specified name
		qz.findPrinter(p.value);
		
		// Automatically gets called when "qz.findPrinter()" is finished.
		window['qzDoneFinding'] = function() {
			var p = document.getElementById('printer');
			var printer = qz.getPrinter();
			
			// Alert the printer name to user
			alert(printer !== null ? 'Printer found: "' + printer + 
				'" after searching for "' + p.value + '"' : 'Printer "' + 
				p.value + '" not found.');
			
			// Remove reference to this function
			window['qzDoneFinding'] = null;
		};
	}
}

/***************************************************************************
* Prototype function for listing all printers attached to the system
* Usage:
*    qz.findPrinter('\\{dummy_text\\}');
*    window['qzDoneFinding'] = function() { alert(qz.getPrinters()); };
***************************************************************************/
function findPrinters() {
	if (isLoaded()) {
		// Searches for a locally installed printer with a bogus name
		qz.findPrinter('\\{bogus_printer\\}');
		
		// Automatically gets called when "qz.findPrinter()" is finished.
		window['qzDoneFinding'] = function() {
			// Get the CSV listing of attached printers
			var printers = qz.getPrinters().split(',');
			for (i in printers) {
				alert(printers[i] ? printers[i] : 'Unknown');      
			}
			
			// Remove reference to this function
			window['qzDoneFinding'] = null;
		};
	}
}



/***************************************************************************
* Prototype function for printing raw ZPL commands
* Usage:
*    qz.append('^XA\n^FO50,50^ADN,36,20^FDHello World!\n^FS\n^XZ\n');
*    qz.print();
***************************************************************************/     
function printZPL(param) {
	//if (notReady()) { return; }
	 //alert("esvk");
	// Send characters/raw commands to qz using "append"
	// This example is for ZPL.  Please adapt to your printer language
	// Hint:  Carriage Return = \r, New Line = \n, Escape Double Quotes= \"
	// qz.append('^XA\n^FO50,50^ADN,36,20^FDEsvk World!\n^FS\n^XZ\n');
	//qz.append('^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD29^JUS^LRN^CI0^XZ ~DG000.GRF,02816,044, ,::::::::::::::::::::::::::U010J040gH0C0g04,U0380I0F0gG08E0g0F,U07C0H07E0U040I01FC010W07E,U0780H0E20g01E0T020J0E2,U0180H020M040M070J0400180P030J020P030,V080Q040M0F880K01E0P0380N020K038,gG040L040K06070C0I0401C0P07C40I04001C0K070,gG020L0E0J0278F080I0803E0P03860I02007E0K024,V040I060K01E0J07F071C0I0C01C0P018C0I0601FC0K04F,V020I020L060J072020C0I080080P010E0I0201F80K07E,V070I0E0L060J010070C0H01C00C0P01060I0E03C0L0F6,V060H03E0L0200E0J020C0H03C0080Q0860H03E0380L020,V0F0H0H7H010I0701F8001031C001F401C0N0701870H0H703C0,V0F800E20010I0203F8001030C0H08400C020L0F00820H0E201E0,K070I0380I07001C70030H04607FC00301040018C004070401101F0187001C701F0H010I0H40,K0F80H07C0I03800FE003800E2003E003810E003FE004060202200F80820H0FE00B80H080H0H80,J01FC0H07F0I01801FF001800E7001F0018186007FE0040704037013C1C7001FF001C001C100DC0,J01FE0H0FE0I01803FB001800E20H0F8018186003F200203040230218082003FB0H0E001C180880,J0H1F0H01F0I0180071001800670H07C018186001E7006011E073079C0C70H0710H070H0C7C1CC0,L0E0I07800FF80H01FHF80023FNF860I03FTFC20I01FHFE0H07FIFC0,J01FF001FF801FF80H01FHF80027FNF840I03FTFC30I01FIFI07FIFC0,J03FF001FF800FF80I0IF80023FNF840I01FKFBFIFCFHFC20J0JFI07EFHFC0,J07FF001FF8007F80I07FF80061FNF840I01FKF1FCFF01FFC60J07FHFI0FC7F3C0,H0387803E3800E0S0E0gR020Q0C,H07C4001E3001F0R01E0hJ03C,H0386001E20H0E0O02003C0H020h078,H07C6001E3001F0N06770FC0H070U04F0g061F0,H0382003E30H0E0N03E1FF80H03C0T0FE0g03FE0,J070I010R0731FF0I07C0S01F40g01FC0,J020I0180T03C0I0380T020gH0380,J010I0180g010,J020J080,J010I0180,P080,,::~DG001.GRF,06144,064, ,::::::::::::::::::::::::::::::::::::::::::::::::01F007C007C0H0FE00FIF0FIF070J0F801E0070H07FHF9FHF8001F001FFC00F0F007C007E0J01FC0H07F0K0700F807001E0J07C,03F80FE00FE003FF80FIF0FIF0F80I0F801E00F800FIF9FHFE003F803FHF80F0F803803FF80I07FF003FFC0J0F80F80F800E0J038,01F80FC007E007FFC07FHF07FHF070I01FC01E0070H07FHF9FIFH01F001FHFC0707C03807FFC0I0IF803FFE0J0781FC07001E0J078,03F80FE00FE00FHFE0FIF8FIF0F80H01FE01E00F800FIF9FIF803F803FHFE0F8FE0380FHFE0H01FHFC0FIF80I0780FC0F800E0J078,01DC1DC00EF00701E0F0I0F0I070I01DC01E0070I01C01E00F007FC01C07E0F0FC07C1F81F0H01F07C0FC1F80I07C1FC0F001E0J07C,03EC1BE01EF00F00E0F0I0F0I0F80H039E01E00780H03E01E00F807BC03E03E0F0FE0383E00F8003E03E0F80F80I0383FE0E0H0E0J038,01DC1DC01C70070I070I070I070I039E01E0070I01C01E0070071C01C01F0707F0381C0070H07C0101F007C0I03C1DC1E001E0J078,03EE3BE03CF80FE0H0F80H0F80H0F80H038F01E00F80H01E01E00F80FBE03E00F8F8FF8383E00E0H0F80H01E003E0I03C3CE1E0H0E0J078,01CC19C01C7807F800F0I0F0I070I070F01E0070I01C01E00F00F1E01C00F0F0F7C7C3C0K0780H01E001C0I01C1CE1C001E0J07C,03EE3BE0383807FF00FHFE0FHFE0F80H0F8F81FIF80H03E01FIFH0E0E03E00F8F0FBC38380K0F80H03E003E0I03E38E3E0H0E0J038,01C631C0783C03FFC07FFE07FFE070I070701FIFJ01C01FHFE01E0F01C00707071C383C07F0H070I01C001C0I01C3071C001E0J078,03EE3BE0F83C00FFE0FHFE0FHFE0F80H0F0781FIF80H01E01FHFC01E0F83E00F8F8F9E383C0FF800F80H03E003E0I01E78F3C0H0E0J078,01C731C0701C001FF0FHFE0FHFE070I0E07C1FIFJ01C01FFE001C0701C0070F0F0F7C7C0FF800780H01C001C0I01E7071C001E0J07C,03E733E0FHFE0H03F0F0I0F0I0F80H0IFC1E00780H03E01E1F803FHF83E00F8F0F0F383C0FF800780083E003E0J0E707B80H0E0J038,01C771C0FHFE0I07070I070I070H01FHFC1E0070I01C01E07C03FHF81C007070707383C0070H07801E1E003C0J0F70738001E0J078,03E7FBE0FHFE0E00F8F80H0F80H0F8003FHFE1E00F80H01E01E07E03FHFC3E00F8F8F83F83E00F800FC03E1F003C0J0HF83B80H0E0J078,01C7F1C1FIF0F00F0F0I0F0I070H01FHFE1E0070I01C01E03E07FHFC1C01F0F0F01FC1F0078007C03C0F007C0J0FE03F8001E0J07C,03E3E3E3E00F0F81F0F0I0F0I0F8003801E1E00F80H03E01E03F07803E3E03E0F0F03F81F83F8003F0FE0FC1F80J0FE03F80H0E0J038,01C3E1C1C00707FHF07FHF07FHF07FFE3801F1E0070I01C01E01F07001C1FHFC070701F80FIFI01FHF807FHF01C0H07E01F01E1FHFC787FHF1E,03E3E1E3C00F87FFE0FIF8FIF8FHFEF800F1E00F80H01E01E00F8F803E3FHFC0F8F80F80FHFE0I0IF803FFE03C0H07E03F81E0FHFC787FHF9E,01C1C1C3C007C1FFC0FIF0FIF07FFE70H0F1E0070I01C01E007CF001E1FHFH0F0F007C01FFC0I07FF001FFC01C0H07C01F01E1FHFC787FHF1E,03E3E3E3800380FF00FIF8FIF8FHFEF800F9E00780H03E01E003EE0H0F3FFE00F0F007800FE0J01F80H07F003C0H03E01E01E0FHFC783FHF1E,,::::::::::::::::::::::::^XA ^MMT ^PW900 ^LL0600 ^LS0 ^FT51,133^BQN,2,3 ^FDMA,12.11.14^FS ^FT160,288^XG000.GRF,1,1^FS ^FT96,256^XG001.GRF,1,1^FS ^PQ1,0,1,Y^XZ ^XA^ID000.GRF^FS^XZ ^XA^ID001.GRF^FS^XZ');
	qz.append(param);
	qz.print();
    //qz.append('^FO50,50^ADN,36,20^FDPRINTED USING QZ PRINT PLUGIN ' + qz.getVersion() + '\n');
	//qz.appendImage(getPath() + 'img/image_sample_bw.png', 'ZPLII');
			
	// Automatically gets called when "qz.appendImage()" is finished.
//	window['qzDoneAppending'] = function() {
		// Append the rest of our commands
		//qz.append('^FS\n');
		//qz.append('^XZ\n');  
		
		// Tell the apple to print.
	//	qz.print();
		
		// Remove any reference to this function
	//	window['qzDoneAppending'] = null;
	//};
}

