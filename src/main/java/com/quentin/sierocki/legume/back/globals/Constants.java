package com.quentin.sierocki.legume.back.globals;

public final class Constants {
	public static final String REGEXP_EMAIL = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public static final String ID_USER = "idUser";
	//Security 
	public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    //Message erreur
    public static final String STATUS_COMMAND_INCONNU = "Le status de la commande est inconnue.";
	public static final String STATUS_PRODUCT_INCONNU = "Le status du produit est inconnu.";
	public static final String ERROR_VALIDATION_ID_SELLER = "Vous devez entre un idSeller supérieur à 0.";
	public static final String ERROR_VALIDATION_PRICE = "Vous devez entrer un prix supérieur à 0.";
	public static final String ERROR_VALIDATION_QUANTITY = "Vous devez entrer une quantité supérieure à 0.";
	public static final String ERROR_VALIDATION_COMMAND_PRODUCTS = "La commande doit contenir des sous commandes.";
	public static final String ERROR_VALIDATION_INCOHERENTE_QUANTITY = "Les quantités des sous-commandes doivent être égales à la quantité commandée totale.";
	public static final String ERROR_ID_PRODUCT = "Vous devez entrer un idProduct supérieur à 0.";
	public static final String ERROR_VALIDATION_PRODUCT_TYPE_NAME = "Le type du produit doit être renseigné.";
	public static final String ERROR_VALIDATION_QUANTITY_SUP_INITIAL_QUANTITY = "La quantité ne doit pas être supérieure à la quantité initiale.";
	public static final String ERROR_VALIDATION_USERNAME = "Le username ne peut pas être vide.";
	public static final String ERROR_VALIDATION_PASSWORD =  "Le password ne peut pas être vide.";
	public static final String ERROR_VALIDATION_ADDRESS =  "L'adresse ne peut pas être vide.";
	public static final String ERROR_VALIDATION_CITY = "La ville ne peut pas être vide.";
	public static final String ERROR_VALIDATION_EMAIL = "L'email n'est pas valide.";
	public static final String ERROR_VALIDATION_PHONE = "Le numéro de télpéphone doit comporter 10 chiffres.";
	public static final String ERROR_VALIDATION_LAT_LNG = "La latitude et la longitude ne peuvent être égale à 0.";
	public static final String ERROR_CONVERTION = "Un problème de convertion est survenu.";
	public static final String ERROR_SERVICE = "Un problème est survenu lors de l'opération. Veuillez nous excuser.";
}
