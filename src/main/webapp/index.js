if (!isLoggedIn()){
    pageRefresh();
}

function pageRefresh(){
    if (isLoggedIn()){
        location.replace("http://localhost:8080/index.html");
        // window.location.href = "http://localhost:8080/index.html";
    } else {
        location.replace("http://localhost:8080/login.html")
        // alert("Naam en of wachtwoord was incorrect.")
    }
}

//Checkt of iemand ingelogd is door te kijken of de myJWT key een waarde heeft.
function isLoggedIn(){
    return window.localStorage.getItem("myJWT") !== null;
}

function logout(){
    window.localStorage.removeItem("myJWT");
    pageRefresh();
}

async function initializer(){
    let kookboekObject = await getGerechten();
    if (kookboekObject != null){
        let kookboek = Object.values(kookboekObject)[0];
        let kookboekTitel = Object.keys(kookboekObject)[0];

        let gerechtenArray = [];
        for (var i = 0; i < kookboek.length; i++){
            let gerechtenObject = {};
            gerechtenObject.titel = kookboek[i].titel;
            gerechtenObject.id = kookboek[i].id;
            gerechtenObject.recept = kookboek[i].recept;
            gerechtenArray.push(gerechtenObject);
        }
        document.getElementById('recipe-list').appendChild(makeDishesList(gerechtenArray));

    }
}

async function getGerechten() {
    const token =  window.localStorage.getItem("myJWT");
    console.log(token)

    let response = await fetch("restservices/gebruiker/kookboek", {method:"POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
            jwt: token
        })})

    if (response.status === 200) {
        let myJson = await response.json();
        console.log(myJson)
        return myJson;
    }
    return null;
}

initializer()


function makeDishesList(disheshArray) {
let list = document.createElement('div');

for (var dish of disheshArray){

    let div = document.createElement('div')
    let button = document.createElement('button');
    button.appendChild(document.createTextNode(dish.titel));
    button.style = 'width: 200px;';
    button.value = dish.id;
    button.addEventListener("click", showRecipe.bind(null, event, dish.recept))
    div.appendChild(button);

    let removeButton = document.createElement('button');
    removeButton.appendChild(document.createTextNode('X'));
    removeButton.value = dish.id;
    removeButton.addEventListener("click", removeRecipe.bind(null, event, removeButton.value))

    div.appendChild(removeButton);

    list.appendChild(div);
}
return list;
}

function newRecipe(){
    let essentialArray = [];
    let stepArray = [];

    let recipeBox = document.getElementById('recipe-box');

    document.getElementById('recipe').remove();

    let recipeTag = document.createElement('div');
    recipeTag.id = 'recipe';
    recipeTag.appendChild(document.createTextNode('Nieuw recept:'))
    recipeBox.appendChild(recipeTag);
    let newLine = document.createElement('div');
    recipeTag.appendChild(newLine);
    let recipeTagButton = document.createElement('button');
    recipeTagButton.appendChild(document.createTextNode('Opslaan'));
    recipeTagButton.addEventListener('click', saveNewRecipe.bind(null, essentialArray, stepArray));
    recipeTag.appendChild(recipeTagButton);
    let recipeTitleTagInput = document.createElement('input');
    recipeTitleTagInput.id = 'recipe-title-input';
    recipeTitleTagInput.placeholder = 'Voer hier de titel in.';
    recipeTitleTagInput.style = 'margin-left: 10px'
    recipeTag.appendChild(recipeTitleTagInput);

    let recipeEssentialsTag = document.createElement('div');
    recipeEssentialsTag.id = 'recipe-essentials';
    recipeTag.appendChild(recipeEssentialsTag);

    let recipeStepsTag = document.createElement('div');
    recipeStepsTag.id = 'recipe-steps';
    recipeTag.appendChild(recipeStepsTag);

    let recipeEssentialTagLabel = document.createElement('label');
    recipeEssentialTagLabel.appendChild(document.createTextNode('Benodigdheid:'));
    recipeEssentialsTag.appendChild(recipeEssentialTagLabel);
    let recipeEssentialTagInput = document.createElement('input');
    recipeEssentialTagInput.id = 'recipe-essential-input';
    recipeEssentialTagInput.placeholder = 'Voer de stap hier in.';
    recipeEssentialsTag.appendChild(recipeEssentialTagInput);
    let recipeEssentialTagButton = document.createElement('button');
    recipeEssentialTagButton.appendChild(document.createTextNode('Voeg benodigdheid toe.'));
    recipeEssentialTagButton.addEventListener('click', saveEssential);
    recipeEssentialsTag.appendChild(recipeEssentialTagButton);

    let recipeEssentialsText = document.createElement('p');
    recipeEssentialsText.appendChild(document.createTextNode('Benodigdheden:'));
    recipeEssentialsText.id = 'recipe-essentials-text';
    recipeEssentialsTag.appendChild(recipeEssentialsText);

    let recipeEssentialTagUl = document.createElement('ul');
    recipeEssentialTagUl.id = 'recipe-essentials-ul';
    recipeEssentialsTag.appendChild(recipeEssentialTagUl);

    
    
    let recipeStepTagLabel = document.createElement('label');
    recipeStepTagLabel.appendChild(document.createTextNode('Stap:'));
    recipeStepsTag.appendChild(recipeStepTagLabel);
    let recipeStepTagInput = document.createElement('input');
    recipeStepTagInput.id = 'recipe-step-input';
    recipeStepTagInput.placeholder = 'Voer de stap hier in.';
    recipeStepsTag.appendChild(recipeStepTagInput);
    let recipeStepTagButton = document.createElement('button');
    recipeStepTagButton.appendChild(document.createTextNode('Voeg stap toe.'));
    recipeStepTagButton.addEventListener('click', saveStep);
    recipeStepsTag.appendChild(recipeStepTagButton);

    let recipeStepsText = document.createElement('p');
    recipeStepsText.appendChild(document.createTextNode('Stappen:'));
    recipeStepsText.id = 'recipe-steps-text';
    recipeStepsTag.appendChild(recipeStepsText);

    let recipeStepTagUl = document.createElement('ul');
    recipeStepTagUl.id = 'recipe-steps-ul';
    recipeStepsTag.appendChild(recipeStepTagUl);

    function saveEssential() {
        let recipeEssentialTagUl = document.getElementById('recipe-essentials-ul');
        let essentialInput = document.getElementById('recipe-essential-input').value;
        let recipeEssentialTagLi = document.createElement('li');
        recipeEssentialTagLi.appendChild(document.createTextNode(essentialInput));
        recipeEssentialTagUl.appendChild(recipeEssentialTagLi);
        essentialArray.push(essentialInput);
    }

    function saveStep() {
        let recipeStepTagUl = document.getElementById('recipe-steps-ul');
        let stepInput = document.getElementById('recipe-step-input').value;
        let recipeStepTagLi = document.createElement('li');
        recipeStepTagLi.appendChild(document.createTextNode(stepInput));
        recipeStepTagUl.appendChild(recipeStepTagLi);
        stepArray.push(stepInput);
    }
}

async function saveNewRecipe(essentialArray, stepArray) {
    let recipeTitle =  document.getElementById('recipe-title-input').value;
    let token = window.localStorage.getItem('myJWT');

    if (recipeTitle !== '' && essentialArray.length > 0 && stepArray.length > 0){
        let response = await fetch("restservices/gebruiker/gerecht/nieuw", {method:"POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                jwt: token,
                titel: recipeTitle,
                stappen: stepArray,
                benodigdheden: essentialArray
            })});

        if (response.status === 200){
            'gelukt'
        }
    }
}

function removeRecipe(event, value){
    console.log(value)

}

function showRecipe(event, dish) {
    console.log(dish);

    let recipeBox = document.getElementById('recipe-box');

    document.getElementById('recipe').remove();

    let recipe = document.createElement('div');
    recipe.id = 'recipe';
    recipe.appendChild(document.createTextNode(dish.titel))
    recipeBox.appendChild(recipe);


    let recipeEssentials = document.createElement('div');
    recipeEssentials.id = 'recipe-essentials';
    recipe.appendChild(recipeEssentials);

    let recipeSteps = document.createElement('div');
    recipeSteps.id = 'recipe-steps';
    recipe.appendChild(recipeSteps);

    if (document.getElementById('recipe-essentials-text')){document.getElementById('recipe-essentials-text').remove()}
    let recipeEssentialsDiv = document.getElementById('recipe-essentials')
    let recipeEssentialsText = document.createElement('p');
    recipeEssentialsText.appendChild(document.createTextNode('Benodigdheden:'))
    recipeEssentialsText.id = 'recipe-essentials-text';
    recipeEssentialsDiv.appendChild(recipeEssentialsText);

    if (document.getElementById('recipe-essentials-ul')){document.getElementById('recipe-essentials-ul').remove()}
    essentialList(dish.benodigdheden);


    if (document.getElementById('recipe-steps-text')){document.getElementById('recipe-steps-text').remove()}
    let recipeStepsDiv = document.getElementById('recipe-steps')
    let recipeStepsText = document.createElement('p');
    recipeStepsText.appendChild(document.createTextNode('Stappen:'))
    recipeStepsText.id = 'recipe-steps-text';
    recipeStepsDiv.appendChild(recipeStepsText);

    if (document.getElementById('recipe-steps-ul')){document.getElementById('recipe-steps-ul').remove()}
    stepList(dish.stappen);
}

function essentialList (essentialArray){
    let div = document.getElementById('recipe-essentials')

    let essentials = document.createElement('ul');
    essentials.id = 'recipe-essentials-ul';
    div.appendChild(essentials);

    for (var i = 0; i < essentialArray.length; i++){
        let essential = document.createElement('li')
        essential.appendChild(document.createTextNode(essentialArray[i]))
        essentials.appendChild(essential)
    }
}

function stepList (stepArray) {
    let div = document.getElementById('recipe-steps')

    let steps = document.createElement('ul');
    steps.id = 'recipe-steps-ul';
    div.appendChild(steps);

    for (var i = 0; i < stepArray.length; i++){
        let step = document.createElement('li')
        step.appendChild(document.createTextNode(stepArray[i]))
        steps.appendChild(step)
    }
}

