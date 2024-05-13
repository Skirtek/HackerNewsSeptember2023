const BASE_URL = window.location.origin;

// pobieranie danych
function fetchData(route) {
    // ZAMIAST PLUSÓW UŻYWAĆ INTERPOLACJI
    console.log("Requested route " + route)
    // przechodzimy pod url
    window.history.pushState(null, null, route)

    const toFetch = `${BASE_URL}/hackernews_war_exploded/api${route}`
    console.log("Fetching from " + toFetch)

    fetch(toFetch)
        .then(response => response.json())
        .then(renderNews)
        .catch(error => {
            console.log(error)
            const newsDiv = document.getElementById("news")
            newsDiv.innerHTML = "<div class='alert alert-danger' role='alert'>Something went wrong</div>"
        })
}

// obsługa menu
function menuHandler(event) {
    const element = event.target
    const category = element.dataset.category

    event.preventDefault();

    if (category) {
        fetchData(`?category=${category}&page=1`)
    }
}

// paginacja
function paging(event) {
    // pobieramy query string
    const params = new URLSearchParams(window.location.search)
    // jaki przycisk kliknięto
    const pageValue = event.target.id === "prev" ? -1 : 1;

    const newPage = parseInt(params.get("page")) + pageValue

    // to potem dodać, nie robić tego na początku
    if (newPage < 1) {
        return
    }

    params.set("page", newPage)

    fetchData(`?${params.toString()}`)
}

function bindEventListeners() {
    const menuElements = document.querySelectorAll(".nav-link");

    menuElements.forEach(menuItem => {
        menuItem.addEventListener("click", menuHandler)
    })

    const prev = document.getElementById("prev")
    prev.addEventListener("click", paging)

    const next = document.getElementById("next")
    next.addEventListener("click", paging)
}

function renderNews(news) {
    const newsDiv = document.getElementById("news")
    // reset
    newsDiv.innerHTML = "";

    // _blank, Opens the linked document in a new window or tab

    news.forEach((newsItem) => {
        const card = `<div class="card-body">
            <div class="card-title"><a href="${newsItem.url}" target="_blank">${newsItem.title}</a></div>
            <p class="card-text">${newsItem.time_ago}<br/>${newsItem.user}</p>
        </div>`

        const cardElement = document.createElement('div')
        cardElement.classList.add("card")
        cardElement.classList.add("text-white")
        cardElement.classList.add("bg-dark")
        cardElement.classList.add("mb-3")
        cardElement.style.width = "18rem"
        cardElement.innerHTML = card;
        newsDiv.appendChild(cardElement)
    })
}

function getRoute() {
    // bierzemy jako stronę główną
    const path = window.location.pathname === BASE_URL ? "/newest" : window.location.pathname
    const search = window.location.search === "" ? "?page=1" : window.location.search

    return `${path}${search}`;
}

// konfiguracja i pobranie na początku
(function () {
    bindEventListeners()
    // domyślna strona główna
    const route = "?category=news&page=1"
    console.log("route is: " + route)
    console.log("base url is: " + BASE_URL)
    fetchData(route)
})();