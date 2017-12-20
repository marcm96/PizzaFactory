import {Pizza} from "./pages/model/Pizza";

export const data = [
    {   key: 1,
        pizza: new Pizza(
            "Margherita",
            "double cheese",
            "500gr",
            "15"
        )
    },
    {
        key: 2,
        pizza: new Pizza(
            "Prosciutto e Funghi",
            "cheese, ham, black olives, corn",
            "500gr",
            "20"
        )
    },
    {   key: 3,
        pizza: new Pizza(
            "Diavola",
            "cheese, ham, beef, sausage, salami",
            "650gr",
            "21"
        )
    },
    {
        key: 4,
        pizza: new Pizza(
            "Hawaiian",
            "cheese, ham, pineapple, roasted red peppers, provolone",
            "550gr",
            "18"
        )
    },
    {
        key: 5,
        pizza: new Pizza(
            "Quarto Formaggi",
            "double cheese, provolone, parmesan",
            "500gr",
            "19"
        )
    },
];