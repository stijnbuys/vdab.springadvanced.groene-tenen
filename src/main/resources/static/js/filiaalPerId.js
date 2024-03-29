document.getElementById("zoeken").addEventListener("click", function()
{
 document.getElementById("naam").innerHTML = "";
 document.getElementById("adres").innerHTML = "";
    var idElement = document.getElementById("id");
 if (! idElement.checkValidity())
 {
 document.getElementById("ongeldigeId").style.display = "block";
 } else {
     fetch(idElement.value).then(verwerkResponse);
 }
    idElement.focus();
});
function verwerkResponse(response)
{
 switch (response.status)
 {
     case 200:
         response.json().then(verwerkJson);
 break;
     case 404:
         document.getElementById("filiaalBestaatNiet").style.display="block";
 break;
     default:
         document.getElementById("technischProbleem").style.display="block";
 }
}
function verwerkJson(filiaalResource)
{
 var filiaal = filiaalResource.filiaal;
 document.getElementById('naam').innerHTML = filiaal.naam;
 var adres = filiaal.adres;
    document.getElementById('adres').innerHTML = adres.straat + ' ' +
        adres.huisNr + ' ' + adres.postcode + ' ' + adres.gemeente;
}