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

function initializer(){

}

var testarray = ['hoi', 'dit', 'is', 'een', 'test'];


function makeRecipeList(array) {
let list = document.createElement('div');

for (var i = 0; i < array.length; i++){
    let div = document.createElement('div')
    let button = document.createElement('button');
    button.appendChild(document.createTextNode(array[i]));
    button.style = 'width: 200px;';
    button.value = array[i];
    button.addEventListener("click", showRecipe.bind(null, event, button.value))
    div.appendChild(button);

    let removeButton = document.createElement('button');
    removeButton.appendChild(document.createTextNode('X'));
    removeButton.value = array[i];
    removeButton.addEventListener("click", removeRecipe.bind(null, event, removeButton.value))

    div.appendChild(removeButton);

    list.appendChild(div);
}
return list;
}

function newRecipe(){
    if (document.getElementById('recipe-essentials')){var x = document.getElementById('recipe-essentials-ul'); x.remove()}
    if (document.getElementById('steps-essentials')){var x = document.getElementById('recipe-essentials-ul'); x.remove()}
}

function removeRecipe(event, value){
    console.log(value)

}

function showRecipe(event, value) {
    console.log(value);
    let recipe = document.getElementById('recipe');

    let recipeEssentials = document.createElement('div');
    recipeEssentials.id = 'recipe-essentials';
    recipe.appendChild(recipeEssentials);

    let recipeSteps = document.createElement('div');
    recipeSteps.id = 'recipe-steps';
    recipe.appendChild(recipeSteps);

    let essentialArray = ['Rijst', 'Water', 'Meel'];
    let stepArray = ['Maak de rijst nat', 'Kook het water', 'Zeef de meel'];

    if (document.getElementById('recipe-essentials-text')){var a = document.getElementById('recipe-essentials-text'); a.remove()}
    let recipeEssentialsDiv = document.getElementById('recipe-essentials')
    let recipeEssentialsText = document.createElement('p');
    recipeEssentialsText.appendChild(document.createTextNode('Benodigdheden:'))
    recipeEssentialsText.id = 'recipe-essentials-text';
    recipeEssentialsDiv.appendChild(recipeEssentialsText);

    if (document.getElementById('recipe-essentials-ul')){var b = document.getElementById('recipe-essentials-ul'); b.remove()}
    essentialList(essentialArray);



    if (document.getElementById('recipe-steps-text')){var c = document.getElementById('recipe-steps-text'); c.remove()}
    let recipeStepsDiv = document.getElementById('recipe-steps')
    let recipeStepsText = document.createElement('p');
    recipeStepsText.appendChild(document.createTextNode('Stappen:'))
    recipeStepsText.id = 'recipe-steps-text';
    recipeStepsDiv.appendChild(recipeStepsText);

    if (document.getElementById('recipe-steps-ul')){var d = document.getElementById('recipe-steps-ul'); d.remove()}
    stepList(stepArray);
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

document.getElementById('recipe-list').appendChild(makeRecipeList(testarray));