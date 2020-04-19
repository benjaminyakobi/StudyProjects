#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
#include <math.h>
#include <time.h>

#define sales_representative 4
#define LINESIZE 200
#define SIZE 40
#define repricing_col 4
#define opportunity_col 9

/*EMPLOYEE MENU*/
void employee_menu(char* connected_as); // Print Employee-Menu
void print_database(char* db_name); // Open the Opportunity-Table Database and print it
void Search_existing_customer();
void Update_customer(long int index);
void Uptade_deal(long int deal_id, long int customer_index);
void Add_new_customer(long ID);
void self_reports(char* name); // Employee Self-Reports

							   /*MANAGER MENU*/
void manager_menu(char* connected_as);
void all_reports_menu(char* connected_as); // All Reports Menu to choose specific report
void repricing_products(); // Repricing products in the database

						   /*ALL REPORTS MENU FOR MANAGER ONLY!*/
void report_by_name(); // { scanf char* name->calls self_reports function }
void new_customers_last_month(); // from Opportunity Table (MAYBE WE HAVE TO ADD 'DATE OF JOIN' IN THE CUSTOMER DETAILS)
void new_customers_last_year();// from Opportunity Table (MAYBE WE HAVE TO ADD 'DATE OF JOIN' IN THE CUSTOMER DETAILS)
void successful_deals_last_month(); // from Opportunity Table, Status: "Win"
void successful_deals_last_year(); // from Opportunity Table, Status: "Win"

								   /*HELPER FUNCTIONS*/
void add_oppurtunity(long int deal_index, long int customer_index);
void write_oppurtunity_data();
void Write_to_deal_data();
void Close_client_Data(FILE *name);
FILE* Open_Client_Data();
FILE* Write_Client_Data();
void Write_to_Client_Data();
void file_insert_client();
void file_insert_oppurtunity();
void file_insert_deal();
void add_new_deal(long int customer_index);
long int from_char_to_long(char *field);
//void boldgreen(); //Set the text to the color bold green
//void red(); //Set the text to the color red
//void cyan(); //Set the text to the color green
//void yellow(); //Set the text to the color yellow
//void reset(); //Set the text to the default
FILE* Write_Reprice();
int login_input(char* connected_as);
int login(char* user, char* password);
char*** make_array(char*** array, char* database_name, int database_columns);
void choose_type_of_deal(long int index);
void choose_type_of_products(long int index);
int number_of_product();
void choose_a_deal_status(long int index);
float calculate_price(long int index);
void Free_client();
void Free_deal();
void Free_oppurtunity();

/*GLOBAL VARIABLES & STRUCTS*/
struct date {
	int day;
	int month;
	int year;
};
struct client {
	long  id;
	char* firstName;
	char* lastName;
	unsigned long int phoneNumber;
	char* emailAdress;
	char* city;
	char* livingAdress;
	int number_of_deal;
	unsigned long int* deal_id;
	char* dealStatus;
	char* paymentDetails;
	char *Status;
	struct date start;
};
struct oppurtunity {
	unsigned long int client_id;
	float worth;
	char *status;
	float confidence;
	char *seler_name;
	struct date last_communication;
	char* product;
	unsigned int number_of_products;
	unsigned long int deal_number;
};
struct deal {
	unsigned long int deal_number;
	unsigned int number_of_products;
	char *type_of_products;
	char *type_of_deal;
	int total_price;
	char *status;
	char *type_of_payment;
	struct date close;
	struct date edit;
};
struct client *customers = NULL;
struct oppurtunity *opp = NULL;
struct deal *deal = NULL;
char *connected_as = NULL;
static unsigned long int number_oppurtunity;
static unsigned long int number_client;
static unsigned long int number_deal;


/******==================================******/
/******==================================******/
/******==================================******/
/******===THE PROGRAM STARTS FROM HERE===******/
/******==================================******/
/******==================================******/
/******==================================******/

int main() {
	file_insert_client();// initialize client's struct
	file_insert_oppurtunity(); // initialize oppurtunity's struct
	file_insert_deal(); // initialize deal's struct
	connected_as = (char*)malloc(SIZE * sizeof(char));
	int log = login_input(connected_as);
	if (log == 1)
		employee_menu(connected_as);
	if (log == 2)
		manager_menu(connected_as);
	return 0;
}

/*THOSE FUNCTIONS COMPLITED & WORKS FINE!*/
void manager_menu(char* connected_as) {
	int manager_choice;
	int customer_ID = 0;
	char*** database_array = NULL;

//	cyan();
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("****************** MANAGER MENU ******************\n");
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("**    1. Print Opportunity Table                **\n");
	printf("%60.s");
	printf("**    2. Search existing customer               **\n");
	printf("%60.s");
	printf("**    3. TO-DO List                             **\n");
	printf("%60.s");
	printf("**    4. All Reports                            **\n");
	printf("%60.s");
	printf("**    5. Print Price List                       **\n");
	printf("%60.s");
	printf("**    6. Repricing Products                     **\n");
	printf("%60.s");
	printf("**    0. Exit                                   **\n");
	printf("%60.s");
	printf("**************************************************\n\n");

//	boldgreen();
	printf("Enter Your Choice: ");
	scanf("%d", &manager_choice);
	while (manager_choice < 0 || manager_choice > 6 && (manager_choice <= '/' || manager_choice >= ':'))
	{
//		red();
		printf("Wrong choice - Enter Your Choice: ");
//		boldgreen();
		fseek(stdin, 0, SEEK_END);
		scanf("%d", &manager_choice);
	}
	switch (manager_choice) {
	case 0:
//		red();
		printf("Good Bye");
		exit(0);
		break;
	case 1:
		print_database("opportunity_table.csv");
		manager_menu(connected_as);
		break;
	case 2:
		Search_existing_customer();
		manager_menu(connected_as);
		break;
	case 3:
		print_database("to_do_list_database.csv");
		manager_menu(connected_as);
		break;
	case 4:
		all_reports_menu(connected_as);
		manager_menu(connected_as);
		break;
	case 5:
		print_database("data_base_repricing.csv");
		manager_menu(connected_as);
		break;
	case 6:
		repricing_products();
		print_database("data_base_repricing.csv");
		manager_menu(connected_as);
		break;
	default:
		break;
	}

}

void employee_menu(char* connected_as) {
	int employee_choice;
	int customer_ID = 0;
	char*** database_array = NULL;

//	cyan();
	printf("%60.s");
	printf("***************************************************\n");
	printf("%60.s");
	printf("****************** EMPLOYEE MENU ******************\n");
	printf("%60.s");
	printf("***************************************************\n");
	printf("%60.s");
	printf("**    1. Opportunity Table                       **\n");
	printf("%60.s");
	printf("**    2. Search existing customer                **\n");
	printf("%60.s");
	printf("**    3. TO-DO List                              **\n");
	printf("%60.s");
	printf("**    4. Self Reports                            **\n");
	printf("%60.s");
	printf("**    5. Print Price List                        **\n");
	printf("%60.s");
	printf("**    0. Exit                                    **\n");
	printf("%60.s");
	printf("***************************************************\n\n");

	//boldgreen();
	printf("Enter Your Choice: ");
	scanf("%d", &employee_choice);

	while (employee_choice < 0 || employee_choice > 5 && (employee_choice <= '/' || employee_choice >= ':')) {
//		red();
		printf("Wrong choice - Enter Your Choice: ");
//		boldgreen();
		fseek(stdin, 0, SEEK_END);
		scanf("%d", &employee_choice);
	}
	switch (employee_choice) {
	case 0:
//		red();
		printf("Good Bye");
		exit(0);
		break;
	case 1:
		print_database("opportunity_table.csv");
		employee_menu(connected_as);
		break;
	case 2:
		Search_existing_customer();
		employee_menu(connected_as);
		break;
	case 3:
		print_database("to_do_list_database.csv");
		employee_menu(connected_as);

		break;
	case 4:
		self_reports(connected_as);
		employee_menu(connected_as);
		break;
	case 5:
		print_database("data_base_repricing.csv");
		employee_menu(connected_as);
		break;
	default:
		break;
	}
}

void file_insert_oppurtunity() {
	FILE *oppu = fopen("opportunity_table.csv", "r");
	if (oppu == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
	}
	char information[150];
	char field[35];
	char num;
	int index = 0, i = 0, j = 0, num_field = 0;
	number_oppurtunity = 0;
	while (!feof(oppu))
	{
		fgets(information, 149, oppu);
		opp = (struct oppurtunity *)realloc(opp, (number_oppurtunity + 1) * sizeof(struct oppurtunity));
		num_field = 0;
		for (index = 0; information[index] != '\0'; ++index) {
			i = 0;
			while (information[index] != ',' && i < 35 && information[index] != '\n' && !feof(oppu)) {
				field[i] = information[index];
				++index;
				++i;
			}
			field[i] = '\0';
			if (num_field == 0) {
				opp[number_oppurtunity].client_id = from_char_to_long(field);
			}
			else if (num_field == 1) {
				opp[number_oppurtunity].worth = strtof(field, NULL);
			}
			else if (num_field == 2) {
				opp[number_oppurtunity].status = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (opp[number_oppurtunity].status == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(opp[number_oppurtunity].status, field);
			}
			else if (num_field == 3) {
				opp[number_oppurtunity].confidence = strtof(field, NULL);
			}
			else if (num_field == 4) {
				opp[number_oppurtunity].seler_name = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (opp[number_oppurtunity].seler_name == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(opp[number_oppurtunity].seler_name, field);
			}
			else if (num_field == 5) {
				opp[number_oppurtunity].last_communication.day = from_char_to_long(field);
			}
			else if (num_field == 6) {
				opp[number_oppurtunity].last_communication.month = from_char_to_long(field);
			}
			else if (num_field == 7) {
				opp[number_oppurtunity].last_communication.year = from_char_to_long(field);
			}
			else if (num_field == 8) {
				opp[number_oppurtunity].product = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (opp[number_oppurtunity].product == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(opp[number_oppurtunity].product, field);
			}
			else if (num_field == 9) {
				opp[number_oppurtunity].number_of_products = from_char_to_long(field);
			}
			else if (num_field == 10) {
				opp[number_oppurtunity].deal_number = from_char_to_long(field);
			}
			++num_field;
		}
		++number_oppurtunity;
	}
	number_oppurtunity = number_oppurtunity - 1;//fix write to data
	Close_client_Data(oppu);
}

void file_insert_client() {
	FILE *client = Open_Client_Data();
	char information[150];
	char field[35], temp[20];
	char num;
	int index = 0, i = 0, j = 0, num_field = 0, k = 0, f = 0, num_deal;
	number_client = 0;
	while (!feof(client))
	{
		fgets(information, 149, client);
		customers = (struct client *)realloc(customers, (number_client + 1) * sizeof(struct client));
		num_field = 0;
		for (index = 0; information[index] != '\0'; ++index) {
			i = 0;
			while (information[index] != ',' && i < 35 && information[index] != '\n' && !feof(client)) {
				field[i] = information[index];
				++index;
				++i;
			}
			field[i] = '\0';
			if (num_field == 0) {
				customers[number_client].id = from_char_to_long(field);
			}
			else if (num_field == 1) {
				customers[number_client].firstName = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].firstName == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].firstName, field);
			}
			else if (num_field == 2) {
				customers[number_client].lastName = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].lastName == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].lastName, field);
			}
			else if (num_field == 3) {
				customers[number_client].phoneNumber = from_char_to_long(field);
			}
			else if (num_field == 4) {
				customers[number_client].emailAdress = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].emailAdress == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].emailAdress, field);
			}
			else if (num_field == 5) {
				customers[number_client].city = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].city == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].city, field);
			}
			else if (num_field == 6) {
				customers[number_client].livingAdress = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].livingAdress == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].livingAdress, field);
			}
			else if (num_field == 7) {
				customers[number_client].number_of_deal = from_char_to_long(field);
			}
			else if (num_field == 8) {
				customers[number_client].deal_id = (long int *)malloc(customers[number_client].number_of_deal * sizeof(long int));
				num_deal = 0;
				for (k = 0; field[k] != '\0'; ++k) {
					f = 0;
					while (field[k] != ':')
					{
						temp[f] = field[k];
						k++;
						f++;
					}
					temp[f] = '\0';
					customers[number_client].deal_id[num_deal] = from_char_to_long(temp);
					++num_deal;
				}
			}
			else if (num_field == 9) {
				customers[number_client].dealStatus = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].dealStatus == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].dealStatus, field);
			}
			else if (num_field == 10) {
				customers[number_client].paymentDetails = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].paymentDetails == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].paymentDetails, field);
			}
			else if (num_field == 11) {
				customers[number_client].Status = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (customers[number_client].Status == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(customers[number_client].Status, field);
			}
			else if (num_field == 12) {
				customers[number_client].start.day = from_char_to_long(field);
			}
			else if (num_field == 13) {
				customers[number_client].start.month = from_char_to_long(field);
			}
			else if (num_field == 14) {
				customers[number_client].start.year = from_char_to_long(field);
			}
			++num_field;
		}
		++number_client;
	}
	number_client = number_client - 1;//fix write to data
	Close_client_Data(client);
}

void file_insert_deal() {
	FILE *oppu = fopen("deal_table.csv", "r");
	if (oppu == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
	}
	char information[150];
	char field[35];
	char num;
	int index = 0, i = 0, j = 0, num_field = 0;
	number_deal = 0;
	while (!feof(oppu))
	{
		fgets(information, 149, oppu);
		deal = (struct deal *)realloc(deal, (number_deal + 1) * sizeof(struct deal));
		num_field = 0;
		for (index = 0; information[index] != '\0'; ++index) {
			i = 0;
			while (information[index] != ',' && i < 35 && information[index] != '\n' && !feof(oppu)) {
				field[i] = information[index];
				++index;
				++i;
			}
			field[i] = '\0';
			if (num_field == 0) {
				deal[number_deal].deal_number = from_char_to_long(field);
			}
			else if (num_field == 1) {
				deal[number_deal].number_of_products = from_char_to_long(field);
			}
			else if (num_field == 2) {
				deal[number_deal].type_of_products = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (deal[number_deal].type_of_products == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(deal[number_deal].type_of_products, field);
			}
			else if (num_field == 3) {
				deal[number_deal].type_of_deal = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (deal[number_deal].type_of_deal == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(deal[number_deal].type_of_deal, field);
			}
			else if (num_field == 4) {
				deal[number_deal].total_price = from_char_to_long(field);
			}
			else if (num_field == 5) {
				deal[number_deal].status = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (deal[number_deal].status == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(deal[number_deal].status, field);
			}
			else if (num_field == 6) {
				deal[number_deal].type_of_payment = (char*)malloc((strlen(field) + 1) * sizeof(char));
				if (deal[number_deal].type_of_payment == NULL) {
					printf("\n"); // add the error message
					return 0;
				}
				strcpy(deal[number_deal].type_of_payment, field);
			}
			else if (num_field == 7) {
				deal[number_deal].close.day = from_char_to_long(field);
			}
			else if (num_field == 8) {
				deal[number_deal].close.month = from_char_to_long(field);
			}
			else if (num_field == 9) {
				deal[number_deal].close.year = from_char_to_long(field);
			}
			else if (num_field == 10) {
				deal[number_deal].edit.day = from_char_to_long(field);
			}
			else if (num_field == 11) {
				deal[number_deal].edit.month = from_char_to_long(field);
			}
			else if (num_field == 12) {
				deal[number_deal].edit.year = from_char_to_long(field);
			}
			++num_field;
		}
		++number_deal;
	}
	number_deal = number_deal - 1;// fix write to data
	Close_client_Data(oppu);
}

void Write_to_Client_Data() {
	int i, j;
	FILE *fp = fopen("data_base_client.csv", "w");
	if (fp == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
		return fp;
	}
	for (i = 0; i < number_client; ++i) {
		if (customers[i].phoneNumber != 0) {
			fprintf(fp, "%ld,%s,%s,%ld,%s,%s,%s,%d,",
				customers[i].id, customers[i].firstName, customers[i].lastName, customers[i].phoneNumber, customers[i].emailAdress,
				customers[i].city, customers[i].livingAdress, customers[i].number_of_deal);
			for (j = 0; j < customers[i].number_of_deal; ++j) {
				fprintf(fp, "%ld:", customers[i].deal_id[j]);
			}
			fprintf(fp, ",%s,%s,%s,%d,%d,%d\n", customers[i].dealStatus, customers[i].paymentDetails,
				customers[i].Status, customers[i].start.day, customers[i].start.month, customers[i].start.year);
		}
	}
	Close_client_Data(fp);
}

void all_reports_menu(char* connected_as) {
	int report_choice;
//	cyan();
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("**************** ALL REPORTS MENU ****************\n");
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("**    1. Report By Name of Employee             **\n");
	printf("%60.s");
	printf("**    2. Last Month New Customers               **\n");
	printf("%60.s");
	printf("**    3. Last Year New Customers                **\n");
	printf("%60.s");
	printf("**    4. Last Month Successful Deals            **\n");
	printf("%60.s");
	printf("**    5. Last Year Successful Deals             **\n");
	printf("%60.s");
	printf("**    0. Go Back To Previous Menu               **\n");
	printf("%60.s");
	printf("**************************************************\n\n");

//	boldgreen();
	printf("Enter Your Choice: ");
	scanf("%d", &report_choice);

	while (report_choice < 0 || report_choice > 5 && (report_choice <= '/' || report_choice >= ':')) {
//		red();
		printf("Wrong choice - Enter Your Choice: ");
//		boldgreen();
		fseek(stdin, 0, SEEK_END);
		scanf("%d", &report_choice);
	}
	switch (report_choice) {
	case 1:
		report_by_name();
		all_reports_menu(connected_as);
		break;
	case 2:
		new_customers_last_month();
		all_reports_menu(connected_as);
		break;
	case 3:
		new_customers_last_year();
		all_reports_menu(connected_as);
		break;
	case 4:
		successful_deals_last_month();
		all_reports_menu(connected_as);
		break;
	case 5:
		successful_deals_last_year();
		all_reports_menu(connected_as);
		break;
	case 0:
		manager_menu(connected_as);
		break;
	default:

		break;
	}
}

void new_customers_last_month() {
	FILE *last_month_client = fopen("last_month_new_customers_report.csv", "w");
	if (last_month_client == NULL) {
//		red();
		printf("File failed to open!\n");
//		reset();
		return;
	}
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int this_month = aTm->tm_mon + 1;
	int i,j;
	for (i = 0; i < number_client; ++i) {
		if ((customers[i].start.year == aTm->tm_year + 1900) || ((customers[i].start.year == aTm->tm_year + 1899) && (this_month == 1))) {
			if (((this_month == 1) && (customers[i].start.month == 12)) || (customers[i].start.month == this_month - 1)) {
				fprintf(last_month_client, "%ld,%s,%s,%ld,%s,%s,%s,%d",
					customers[i].id, customers[i].firstName, customers[i].lastName, customers[i].phoneNumber, customers[i].emailAdress,
					customers[i].city, customers[i].livingAdress, customers[i].number_of_deal);
				for (j = 0; j < customers[i].number_of_deal; ++j) {
					fprintf(last_month_client, "%ld:", customers[i].deal_id[j]);
				}
				fprintf(last_month_client, ",%s,%s,%s,%d,%d,%d\n", customers[i].dealStatus, customers[i].paymentDetails,
					customers[i].Status, customers[i].start.day, customers[i].start.month, customers[i].start.year);
			}
		}
	}
//	yellow();
	printf("The File is Ready in Your Folder!\n");
//	reset();
	fclose(last_month_client);
	print_database("last_month_new_customers_report.csv"); // this line added to print the report
}

void new_customers_last_year() {
	FILE *last_year_client = fopen("last_year_new_customers_report.csv", "w");
	if (last_year_client == NULL) {
//		red();
		printf("File failed to open!\n");
		return;
	}
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int this_year = aTm->tm_year + 1900;
	int i, j;
	for (i = 0; i < number_client; ++i) {
		if (customers[i].start.year == this_year - 1) {
			fprintf(last_year_client, "%ld,%s,%s,%ld,%s,%s,%s,%d",
				customers[i].id, customers[i].firstName, customers[i].lastName, customers[i].phoneNumber, customers[i].emailAdress,
				customers[i].city, customers[i].livingAdress, customers[i].number_of_deal);
			for (j = 0; j < customers[i].number_of_deal; ++j) {
				fprintf(last_year_client, "%ld:", customers[i].deal_id[j]);
			}
			fprintf(last_year_client, ",%s,%s,%s,%d,%d,%d\n", customers[i].dealStatus, customers[i].paymentDetails,
				customers[i].Status, customers[i].start.day, customers[i].start.month, customers[i].start.year);
		}
	}
//	yellow();
	printf("The File is Ready in Your Folder!\n");
//	reset();
	fclose(last_year_client);
	print_database("last_year_new_customers_report.csv"); // this line added to print the report
}

void successful_deals_last_month() {
	FILE *deals = fopen("succesful_deals_last_month.csv", "w");
	if (deals == NULL) {
//		red();
		printf("File failed to open!\n");
//		reset();
		return;
	}
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int this_month = aTm->tm_mon + 1;
	int i;
	for (i = 0; i < number_deal; ++i) {
		if ((deal[i].close.year == aTm->tm_year + 1900) || ((deal[i].close.year == aTm->tm_year + 1899) && (this_month == 1))) {
			if (((this_month == 1) && (deal[i].close.month == 12)) || (deal[i].close.month == this_month - 1)) {
				if (strcmp(deal[i].status, "WON") == 0) {
					fprintf(deals, "%ld,%d,%s,%s,%d,%s,%s,%d,%d,%d,%d,%d,%d\n", deal[i].deal_number, deal[i].number_of_products, deal[i].type_of_products,
						deal[i].type_of_deal, deal[i].total_price, deal[i].status, deal[i].type_of_payment, deal[i].close.day, deal[i].close.month, deal[i].close.year, deal[i].edit.day, deal[i].edit.month, deal[i].edit.year);
				}
			}
		}
	}
//	yellow();
	printf("The File is Ready in Your Folder!\n");
//	reset();
	fclose(deals);
	print_database("succesful_deals_last_month.csv"); // this line added to print the report
}

void successful_deals_last_year() {
	FILE *deals = fopen("succesful_deals_last_year.csv", "w");
	if (deals == NULL) {
//		red();
		printf("File failed to open!\n");
//		reset();
		return;
	}
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int this_year = aTm->tm_year + 1900;
	int i;
	for (i = 0; i < number_oppurtunity; ++i) {
		if ((strcmp(deal[i].status, "WON")) == 0 && (deal[i].close.year == this_year - 1)) {
			fprintf(deals, "%ld,%d,%s,%s,%d,%s,%s,%d,%d,%d,%d,%d,%d\n", deal[i].deal_number, deal[i].number_of_products, deal[i].type_of_products,
				deal[i].type_of_deal, deal[i].total_price, deal[i].status, deal[i].type_of_payment, deal[i].close.day, deal[i].close.month, deal[i].close.year, deal[i].edit.day, deal[i].edit.month, deal[i].edit.year);
		}
	}
//	yellow();
	printf("The File is Ready in Your Folder!\n");
//	reset();
	fclose(deals);
	print_database("succesful_deals_last_year.csv");
}

void self_reports(char* name) {
	char*** array = NULL;
	array = make_array(array, "opportunity_table.csv", opportunity_col);
	int i = 0, j, flag = 0;
	int linesnum = 0;
	char line[LINESIZE] = "";

	FILE *fp = fopen("opportunity_table.csv", "r");
	if (!fp) {
//		red();
		printf("File failed to open!\n");
		exit(1);
	}
	while (fgets(line, LINESIZE, fp))
		linesnum++;
	for (i = 1; i < linesnum; i++)
	{
		if (strcmp(array[i][sales_representative], name) == 0)
		{
//			yellow();
			for (j = 0; j < opportunity_col; j++)
			{
				printf("%20s", array[i][j]);
			}
			puts("");
			flag = 1;
//			reset();
		}
	}
	if (flag == 0) {
//		yellow();
		printf("\n\nNo Opportunity Was Found!\n\n");
	}
}

void repricing_products() { // Repricing products in the database
	char *** array = NULL;
	int i, choice, j;
	float newprice, oldprice;
	char line[LINESIZE];
	array = make_array(array, "data_base_repricing.csv", 4);
	int linesnum = 0;
	FILE *fp = fopen("data_base_repricing.csv", "r");
	if (!fp) {
//		red();
		printf("File failed to open!\n");
//		red();
		exit(1);
	}

	while (fgets(line, LINESIZE, fp))
		linesnum++;

//	boldgreen();
	puts("Choose what price would you like to change:");
	for (i = 1; i < linesnum; i++)
	{
//		yellow();
		printf("%d) %s\n", i, array[i][0]);
	}
//	boldgreen();
	scanf("%d", &choice);
	oldprice = strtof(array[choice][2], NULL);
	puts("enter the new price:");
	scanf("%f", &newprice);
	sprintf(array[choice][2], "%.2f", newprice);
	sprintf(array[choice][1], "%.2f", oldprice);
	sprintf(array[choice][3], "%.3f", fabs(100 - (newprice * 100 / oldprice)));

	fclose(fp);
	FILE *f = Write_Reprice();
	for (i = 0; i < linesnum; ++i)
	{
		for (j = 0; j < repricing_col; j++)
		{
			if (j == repricing_col - 1) {
				fputs(array[i][j], f);
				break;
			}
			fputs(array[i][j], f);
			fputs(",", f);
		}
		fputs("\n", f);
	}
//	reset();
	rewind(fp);
	fclose(fp);
}

void Close_client_Data(FILE *name) {
	fclose(name);
}

FILE* Open_Client_Data() {
	FILE *fp = NULL;
	fp = fopen("data_base_client.csv", "r");
	if (fp == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
		return fp;
	}
	return fp;
}

void Search_existing_customer() {
	unsigned long int search_ID;
	long i;
//	boldgreen();
	printf("\nEnter Customer's ID: ");
	scanf("%ld", &search_ID);
	for (i = 0; i < number_client; ++i) {
		if (customers[i].id == search_ID) {
			Update_customer(i);
			return;
		}
	}
	Add_new_customer(search_ID);
}

void Add_new_customer(long ID) {
	char first_name[15];
	char last_name[15];
	char Email[50];
	char city[20];
	char Adress[20];
	char paymentDetails[10];
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	customers = (struct client *)realloc(customers, (number_client + 1) * sizeof(struct client));
	number_client++;
	//if() - check if NULL
	customers[number_client - 1].id = ID;
	customers[number_client - 1].Status = (char *)malloc(4 * sizeof(char));
	//if() - check if NULL
//	boldgreen();
	strcpy(customers[number_client - 1].Status, "NEW");
//	yellow();
	printf("\nThis customer dose not exist!\n");
	printf("Enter new details bellow:\n\n");
//	boldgreen();
	printf("First name: ");
	getchar();
	gets(first_name);
	customers[number_client - 1].firstName = (char*)malloc((strlen(first_name) + 1) * sizeof(char));
	if (customers[number_client - 1].firstName == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].firstName, first_name);
	printf("Last name: ");
	getchar();
	gets(last_name);
	customers[number_client - 1].lastName = (char*)malloc((strlen(last_name) + 1) * sizeof(char));
	if (customers[number_client - 1].lastName == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].lastName, last_name);
	printf("Phone number: ");
	scanf("%ld", &customers[number_client - 1].phoneNumber);
	printf("Email Adress: ");
	getchar();
	gets(Email);
	customers[number_client - 1].emailAdress = (char *)malloc((strlen(Email) + 1) * sizeof(char));
	if (customers[number_client - 1].emailAdress == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].emailAdress, Email);
	printf("City: ");
	getchar();
	gets(city);
	customers[number_client - 1].city = (char *)malloc((strlen(city) + 1) * sizeof(char));
	if (customers[number_client - 1].city == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].city, city);
	printf("Adress: ");
	getchar();
	gets(Adress);
	customers[number_client - 1].livingAdress = (char *)malloc((strlen(Adress) + 1) * sizeof(char));
	if (customers[number_client - 1].livingAdress == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].livingAdress, Adress);
	printf("payment Details: ");
	getchar();
	gets(paymentDetails);
	customers[number_client - 1].paymentDetails = (char *)malloc((strlen(paymentDetails) + 1) * sizeof(char));
	if (customers[number_client - 1].paymentDetails == NULL) {
		printf("\n"); // add the error message
		return 0;
	}
	strcpy(customers[number_client - 1].paymentDetails, paymentDetails);
	///// Add deal information
	customers[number_client - 1].start.day = aTm->tm_mday;
	customers[number_client - 1].start.month = aTm->tm_mon + 1;
	customers[number_client - 1].start.year = aTm->tm_year + 1900;
	customers[number_client - 1].number_of_deal = 1;
	customers[number_client - 1].deal_id = NULL;
	add_new_deal(number_client - 1);
	Write_to_Client_Data();
//	reset();
}

FILE* Write_Client_Data() {
	FILE *fp = NULL;
	int choose = -1;
//	cyan();
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("*************** OPEN FILE TO WRITE ***************\n");
	printf("%60.s");
	printf("**************************************************\n");
	printf("%60.s");
	printf("**    1. Cliens Data Base                       **\n");
	printf("%60.s");
	printf("**    2. System Users Database                  **\n");
	printf("%60.s");
	printf("**    3. Products Database                      **\n");
	printf("%60.s");
	printf("**    4. Opportunity Table Database             **\n");
	printf("%60.s");
	printf("**    0. Exit                                   **\n");
	printf("%60.s");
	printf("**************************************************\n\n");

//	boldgreen();
	printf("Enter Your Choice: ");
	scanf("%d", &choose);
//	reset();
	while (choose < 0 || choose > 4 && (choose <= '/' || choose >= ':'))
	{
//		red();
		printf("Wrong choice - Enter Your Choice: ");
		fseek(stdin, 0, SEEK_END);
//		boldgreen();
		scanf("%d", &choose);
	}

	switch (choose) {
	case 0:
//		red();
		printf("Good Bye");
		exit(0);
		break;
	case 1:
		fp = fopen("data_base_client.csv", "w");
		break;
	case 2:
		fp = fopen("data_base_log_in.csv", "w");
		break;
	case 3:
		fp = fopen("data_base_repricing.csv", "w");
		break;
	case 4:
		fp = fopen("opportunity_table.csv", "w");
		break;
	}
	if (fp == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
		return fp;
	}
//	yellow();
	printf("\nThe file has successfuly opened!\n\n");
//	reset();
	return fp;
}

long int from_char_to_long(char *field) {
	long int num = 0;
	int i;
	for (i = 0; field[i] != '\0'; ++i) {
		if (field[i] >= '0'&&field[i] <= '9')
			num = num * 10 + (field[i] - '0');
	}
	return num;
}

void print_database(char* db_name)
{
	int i = 0;
	int numofrows = 0;
	char line[LINESIZE] = "";
	char *item = NULL;
	char *comma = NULL;
	int field = 0;
	char info_matrix[50][LINESIZE] = { "" };

	FILE* fp = fopen(db_name, "r");

	if (!fp) {
//		red();
		printf("File failed to open!\n");
		exit(1);
	}


	while (fgets(line, LINESIZE, fp)) {
		strcpy(info_matrix[i], line);
		++i;
		++numofrows;
	}

	char t = ' ';
	for (int u = 0; u < 3; ++u)
		fscanf(fp, "%c", &t);

	if (db_name == "opportunity_table.csv") {
		int width[] = {16,16,16,16,14,10,10,14,20,12,12,12,12,12};

		for (int loop = 0; loop < numofrows; ++loop) {
			int field = 0;
			item = info_matrix[loop];
			while (*item && (comma = strpbrk(item, ",\n"))) {//pointer to each comma and the newline
//				yellow();
				printf("%-*.*s", width[field], comma - item, item);
				item = comma + 1;//skip the comma or newline
				field++;
			}
			printf("\n");
		}
//		reset();
	}

	if (db_name == "to_do_list_database.csv") {
		int width[] = { 8, 8, 35, 20, 16, 16, 16, 16, 16, 16 };
		for (int loop = 0; loop < numofrows; ++loop) {
			int field = 0;
			item = info_matrix[loop];
			while (*item && (comma = strpbrk(item, ",\n"))) {//pointer to each comma and the newline
//				yellow();
				printf("%-*.*s", width[field], comma - item, item);
				item = comma + 1;//skip the comma or newline
				field++;
			}
			printf("\n");
		}
//		reset();
	}

	if (db_name == "data_base_repricing.csv") {
		int width[] = { 25,16,26,13 };
		for (int loop = 0; loop < numofrows; ++loop) {
			int field = 0;
			item = info_matrix[loop];
			while (*item && (comma = strpbrk(item, ",\n"))) {//pointer to each comma and the newline
//				yellow();
				printf("%-*.*s", width[field], comma - item, item);
				item = comma + 1;//skip the comma or newline
				field++;
			}
			printf("\n");
		}
//		reset();
	}

	if ((db_name == "last_year_new_customers_report.csv") || (db_name == "last_month_new_customers_report.csv")) {
		int width[] = {12,12,12,12,28,12,12,12,12,12,12,12,12,12};
		for (int loop = 0; loop < numofrows; ++loop) {
			int field = 0;
			item = info_matrix[loop];
			while (*item && (comma = strpbrk(item, ",\n"))) {//pointer to each comma and the newline
//				yellow();
				printf("%-*.*s", width[field], comma - item, item);
				item = comma + 1;//skip the comma or newline
				field++;
			}
			printf("\n");
		}
//		reset();
	}

	if ((db_name == "succesful_deals_last_month.csv") || (db_name == "succesful_deals_last_year.csv")) {
		int width[] = {12,10,20,14,12,12,12,12,8,8,8,8,8,8};
		for (int loop = 0; loop < numofrows; ++loop) {
			int field = 0;
			item = info_matrix[loop];
			while (*item && (comma = strpbrk(item, ",\n"))) {//pointer to each comma and the newline
//				yellow();
				printf("%-*.*s", width[field], comma - item, item);
				item = comma + 1;//skip the comma or newline
				field++;
			}
			printf("\n");
		}
//		reset();
	}
	printf("\n\n");
}

char*** make_array(char*** db_array, char* db_name, int db_columns) {
	int i = 0, j = 0, k = 0, l = 0, m = 0;
	char temp[250];
	int linesnum = 0;
	FILE* fp;
	char line[LINESIZE];
	fp = fopen(db_name, "r");
	if (!fp) {
//		red();
		printf("File failed to open!\n");
		exit(1);
	}
	while (fgets(line, LINESIZE, fp))
		linesnum++;

	db_array = (char*)malloc(sizeof(char*)*linesnum);
	rewind(fp);
	for (i; i < linesnum; ++i) {
		db_array[i] = (char*)malloc(sizeof(char*)*db_columns);
	}

	while (fgets(line, LINESIZE, fp)) {
		for (i = 0; i < db_columns; ++i) {

			while (line[j] != ','&& line[j] != '\n')
			{
				temp[k] = line[j];
				++k;
				++j;
			}
			++j;
			temp[k] = '\0';
			k = 0;
			db_array[l][i] = malloc(strlen(temp) + 1);
			strcpy(db_array[l][i], temp);
		}
		l++;
		j = 0;
	}
	//fclose(fp); // chnage 2
	return db_array;
}

int login(char* user, char* password) {
	/*
	The function gets char* of username and password.
	The function returns: 1 if the user is a employee, 2 if manager,0 if there is a wrong login details.
	there needs to be a csv file where each row conatins 3 clumns: username,password and employee type.
	*/
	FILE* fp;
	int i = 0;
	int j = 0;
	char line[LINESIZE];
	char fUser[LINESIZE];
	char fPassword[LINESIZE];
	char type[LINESIZE];
	fp = fopen("data_base_log_in.csv", "r");
	if (!fp) {
//		red();
		printf("File failed to open!\n");
		exit(1);
	}
	while (fgets(line, LINESIZE, fp)) {//gets a row each time every clumn separated by comas
		i = 0;
		j = 0;
		while (line[i] != ',')
		{
			if ((line[i] >= 'a' && line[i] <= 'z') || (line[i] >= 'A' && line[i] <= 'Z') || (line[i] >= '0'&&line[i] <= '9')) {//gets username and clears junk
				fUser[j] = line[i];
				++j;
			}
			++i;
		}
		fUser[j] = '\0';
		if (strcmp(user, fUser) == 0)
		{
			j = 0;
			++i;
			while (line[i] != ',') {//gets password from line
				fPassword[j] = line[i];
				++j;
				++i;
			}
			fPassword[j] = '\0';

			if (strcmp(password, fPassword) == 0) {
				j = 0;
				++i;
				while (line[i] != '\n') {//gets type from line
					type[j] = line[i];
					++j;
					++i;
				}
				type[j] = '\0';
				if (strcmp(type, "manager") == 0) {
					return 2;
				}
				if (strcmp(type, "employee") == 0) {
					return 1;
				}
			}
		}
	}
	return 0;
}

int login_input(char* connected_as) {
	int log = 0;
	char user[LINESIZE];
	char password[LINESIZE];
	do
	{
//		boldgreen();
		printf("Enter username: ");
		gets(user);
		printf("Enter password: ");
		gets(password);
		log = login(user, password);
		if (log == 0) {
//			red();
			puts("Wrong username or password try again");
		}
	} while (log == 0);
	strcpy(connected_as, user);
	return log;
}

FILE* Write_Reprice() {//opens a file for repricing database
	FILE* fp = NULL;
	fp = fopen("data_base_repricing.csv", "w");
	if (!fp) {
//		red();
		printf("File failed to open!\n");
		exit(1);
	}
	return fp;
}

void report_by_name() {
	char name[40] = "";
//	boldgreen();
	puts("Enter Employee First-Name: ");
	scanf("%s", &name);
	self_reports(name);
}

void choose_type_of_deal(long int index)
{
	int dealStatus_choice = -1;
	printf("\n1.    Regular\n2.    Buisness\n\nEnter type of deal: ");
	scanf("%d", &dealStatus_choice);
	while ((dealStatus_choice < 1 || dealStatus_choice > 2) && (dealStatus_choice <= '/' || dealStatus_choice >= ':')) {
//		red();
		printf("Wrong choice - Enter Your Choice: ");
		fseek(stdin, 0, SEEK_END);
//		boldgreen();
		scanf("%d", &dealStatus_choice);
	}
	switch (dealStatus_choice) {
	case 1:
		deal[index].type_of_deal = (char*)malloc(strlen("REGULAR") + 1 * sizeof(char));
		strcpy(deal[index].type_of_deal, "REGULAR");
		break;
	case 2:
		deal[index].type_of_deal = (char*)malloc(strlen("BUISNESS") + 1 * sizeof(char));
		strcpy(deal[index].type_of_deal, "BUISNESS");
		break;
	default:
		break;
	}

	///char tmp[10];
	///boldgreen();
	///printf("\nPlease Enter Type of Deal - 'REGULAR' or 'BUSINESS': ");
	///scanf("%s", tmp);
	///reset();
	///deal[number_deal].type_of_deal = (char*)malloc(strlen(tmp) + 1 * sizeof(char));
	///strcpy(deal[number_deal].type_of_deal, tmp);
}

int number_of_product()
{
	int num;
//	boldgreen();
	printf("\nPlease Enter Numbers of Products: ");
	scanf("%d", &num);
//	reset();
	return num;

}

void choose_a_deal_status(long int index)
{
	int dealStatus_choice = -1;
	printf("1.    Successful deal\n2.    Unsuccessful deal\n3.    Pending\n\nEneter final status of the deal: ");
	scanf("%d", &dealStatus_choice);

	while ((dealStatus_choice < 1 || dealStatus_choice > 3) && (dealStatus_choice <= '/' || dealStatus_choice >= ':')) {
//		red();
		printf("Wrong choice - Enter Your Choice: ");
		fseek(stdin, 0, SEEK_END);
//		boldgreen();
		scanf("%d", &dealStatus_choice);
	}
	switch (dealStatus_choice) {
	case 1:
		deal[index].status = (char*)malloc(strlen("WON") + 1 * sizeof(char));
		strcpy(deal[index].status, "WON");
		break;
	case 2:
		deal[index].status = (char*)malloc(strlen("LOST") + 1 * sizeof(char));
		strcpy(deal[index].status, "LOST");
		break;
	case 3:
		deal[index].status = (char*)malloc(strlen("PENDING") + 1 * sizeof(char));
		strcpy(deal[index].status, "PENDING");
		break;
	default:
		break;
	}
}

float calculate_price(long int index) {
	float final = 0;
	char*** arr = NULL;
	int num = deal[index].number_of_products, i;
	arr = make_array(arr, "data_base_repricing.csv", 4);
	for (i = 0; i < 10; ++i) {
		if (strcmp(deal[index].type_of_products, arr[i][0]) == 0)
			final = num * strtof(arr[i][2], NULL);
	}
	return final;
}

void choose_type_of_products(long int index) {
	int employee_choice, countmovies = 0, countregualr = 0, counthd = 0, count4k = 0, countremote = 0, countsport = 0;
//	cyan();
	printf("%60.s");
	printf("***************************************************\n");
	printf("%60.s");
	printf("****************** Product MENU *******************\n");
	printf("%60.s");
	printf("***************************************************\n");
	printf("%60.s");
	printf("**    1. Regular Streamer                        **\n");
	printf("%60.s");
	printf("**    2. HD Streamer                             **\n");
	printf("%60.s");
	printf("**    3. 4K Streamer                             **\n");
	printf("%60.s");
	printf("**    4. Remote Controller                       **\n");
	printf("%60.s");
	printf("**    5. Vod Package                             **\n");
	printf("%60.s");
	printf("**    6. Complete Sport Package                  **\n");
	printf("%60.s");
	printf("**    7. Sport 5 Gold Channel                    **\n");
	printf("%60.s");
	printf("**    8. Sport 5 Plus Channel                    **\n");
	printf("%60.s");
	printf("**    9. Movies Package                          **\n");
	printf("%60.s");
	printf("**    0. Exit                                    **\n");
	printf("%60.s");
	printf("***************************************************\n\n");
//	boldgreen();
	printf("Choose type of product: ");
	scanf("%d", &employee_choice);

	while (employee_choice < 0 || employee_choice > 9 && (employee_choice <= '/' || employee_choice >= ':')) {
//		red();
		printf("Wrong choice - Enter Your Choice: ");
		fseek(stdin, 0, SEEK_END);
//		boldgreen();
		scanf("%d", &employee_choice);
	}

	switch (employee_choice) {
	case 0:
//		red();
		printf("Good Bye");
		exit(0);
		break;
	case 1:
		deal[index].type_of_products = (char*)malloc(strlen("REGULAR STREAMER") + 1 * sizeof(char));
		strcpy(deal[index].type_of_products, "REGULAR STREAMER");
		break;
	case 2:
		deal[index].type_of_products = (char*)malloc(strlen("HD STREAMER") + 1 * sizeof(char));
		strcpy(deal[index].type_of_products, "HD STREAMER");
		break;
	case 3:
		deal[index].type_of_products = (char*)malloc(strlen("4K STREAMER") + 1 * sizeof(char));

		strcpy(deal[index].type_of_products, "4K STREAMER");
		break;
	case 4:
		deal[index].type_of_products = (char*)malloc(strlen("REMOTE CONTROLLER") + 1 * sizeof(char));

		strcpy(deal[index].type_of_products, "REMOTE CONTROLLER");
		break;
	case 5:
		deal[index].type_of_products = (char*)malloc(strlen("VOD") + 1 * sizeof(char));
		strcpy(deal[index].type_of_products, "VOD");
		break;
	case 6:
		deal[index].type_of_products = (char*)malloc(strlen("SPORT PACKAGE") + 1 * sizeof(char));
		strcpy(deal[index].type_of_products, "SPORT PACKAGE");
		break;
	case 7:
		deal[index].type_of_products = (char*)malloc(strlen("SPORT5 GOLD") + 1 * sizeof(char));

		strcpy(deal[index].type_of_products, "SPORT5 GOLD");
		break;
	case 8:
		deal[index].type_of_products = (char*)malloc(strlen("SPORT5 PLUS") + 1 * sizeof(char));

		strcpy(deal[index].type_of_products, "SPORT5 PLUS");
		break;
	case 9:
		deal[index].type_of_products = (char*)malloc(strlen("MOVIES PACKAGE") + 1 * sizeof(char));
		strcpy(deal[index].type_of_products, "MOVIES PACKAGE");
		break;
	default:
		break;
	}
}

void add_new_deal(long int customer_index) {
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	deal = (struct deal *)realloc(deal, (number_deal + 1) * sizeof(struct deal));
	deal[number_deal].deal_number = deal[number_deal - 1].deal_number + 1;
	choose_type_of_deal(number_deal);
	choose_type_of_products(number_deal);
	deal[number_deal].number_of_products = number_of_product();
	deal[number_deal].total_price = calculate_price(number_deal);
//	boldgreen();
	printf("\nThe total price is: %d\n", deal[number_deal].total_price);
	choose_a_deal_status(number_deal);
	deal[number_deal].type_of_payment = (char *)malloc((strlen(customers[customer_index].paymentDetails) + 1) * sizeof(char));
	strcpy(deal[number_deal].type_of_payment, customers[customer_index].paymentDetails);
	customers[customer_index].deal_id = (long int*)realloc(customers[customer_index].deal_id, customers[customer_index].number_of_deal * sizeof(long int));
	customers[customer_index].deal_id[customers[customer_index].number_of_deal - 1] = deal[number_deal].deal_number;
	customers[customer_index].dealStatus = (char *)malloc((strlen(deal[number_deal].status) + 1) * sizeof(char));
	strcpy(customers[customer_index].dealStatus, deal[number_deal].status);
	if (strcmp(deal[number_deal].status, "PENDING") == 0) {
		add_oppurtunity(number_deal, number_client - 1);
	}
	deal[number_deal].close.day = aTm->tm_mday;
	deal[number_deal].close.month = aTm->tm_mon + 1;
	deal[number_deal].close.year = aTm->tm_year + 1900;
	deal[number_deal].edit.day = aTm->tm_mday;
	deal[number_deal].edit.month = aTm->tm_mon + 1;
	deal[number_deal].edit.year = aTm->tm_year + 1900;
	number_deal++;
	Write_to_deal_data();
}

void add_oppurtunity(long int deal_index, long int customer_index) {
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	opp = (struct oppurtunity *)realloc(opp, (number_oppurtunity + 1) * sizeof(struct oppurtunity));
	opp[number_oppurtunity].client_id = customers[customer_index].id;
	opp[number_oppurtunity].worth = deal[deal_index].total_price;
	opp[number_oppurtunity].status = (char *)malloc((strlen(deal[deal_index].status) + 1) * sizeof(char));
	strcpy(opp[number_oppurtunity].status, deal[deal_index].status);
	opp[number_oppurtunity].confidence = 50;
	opp[number_oppurtunity].seler_name = (char *)malloc((strlen(connected_as) + 1) * sizeof(char));
	strcpy(opp[number_oppurtunity].seler_name, connected_as);
	opp[number_oppurtunity].last_communication.day = aTm->tm_mday;
	opp[number_oppurtunity].last_communication.month = aTm->tm_mon + 1;
	opp[number_oppurtunity].last_communication.year = aTm->tm_year + 1900;
	opp[number_oppurtunity].product = (char *)malloc((strlen(deal[deal_index].type_of_products) + 1) * sizeof(char));
	strcpy(opp[number_oppurtunity].product, deal[deal_index].type_of_products);
	opp[number_oppurtunity].number_of_products = deal[deal_index].number_of_products;
	opp[number_oppurtunity].deal_number = deal[deal_index].deal_number;
	++number_oppurtunity;
	write_oppurtunity_data();
}

void Update_customer(long int index) {
	char Email[50];
	char city[20];
	char Adress[20];
	char paymentDetails[10];
	char status[10];
	unsigned int update_filde;
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int flag = 0,j;
	while (flag == 0) {
//		yellow();
		printf("What information do you want to update?\n");
		printf("1. Client's Email\n 2. Client's Adrees\n 3. Client's payment details\n 4. Client's phone number\n 5. Client's Status\n 6. Update client's deal\n 0. Save\n ");
		scanf("%d", &update_filde);
		switch (update_filde)
		{
		case 1:
			// update_filde = 1 , user update the Email adress 
			if (customers[index].emailAdress != NULL) {
				free(customers[index].emailAdress);
			}
			printf("Email Adress: ");
			scanf("%s", &Email);
			customers[index].emailAdress = (char *)malloc((strlen(Email) + 1) * sizeof(char));
			if (customers[index].emailAdress == NULL) {
				printf("\n"); // add the error message
				return 0;
			}
			strcpy(customers[index].emailAdress, Email);
			break;
		case 2:
			// update_filde = 2 , user update the client's Adress
			if (customers[index].city != NULL) {
				free(customers[index].city);
			}
			printf("City: ");
			getchar();
			gets(city);
			customers[index].city = (char *)malloc((strlen(city) + 1) * sizeof(char));
			if (customers[index].city == NULL) {
				printf("\n"); // add the error message
				return 0;
			}
			strcpy(customers[index].city, city);
			if (customers[index].livingAdress != NULL) {
				free(customers[index].livingAdress);
			}
			printf("Adress: ");
			getchar();
			gets(Adress);
			customers[index].livingAdress = (char *)malloc((strlen(Adress) + 1) * sizeof(char));
			if (customers[index].livingAdress == NULL) {
				printf("\n"); // add the error message
				return 0;
			}
			strcpy(customers[index].livingAdress, Adress);
			break;
		case  3:
			// update_filde = 3, user update the paymentDetails
			if (customers[index].paymentDetails != NULL) {
				free(customers[index].paymentDetails);
			}
			printf("Payment Details: ");
			getchar();
			gets(paymentDetails);
			customers[index].paymentDetails = (char *)malloc((strlen(paymentDetails) + 1) * sizeof(char));
			if (customers[index].paymentDetails == NULL) {
				printf("\n"); // add the error message
				return 0;
			}
			strcpy(customers[index].paymentDetails, paymentDetails);
			for (j = 0; j < number_deal; ++j) {
				if (deal[j].deal_number == customers[index].deal_id[customers[index].number_of_deal - 1]) {
					if (deal[j].type_of_payment != NULL) {
						free(deal[j].type_of_payment);
					}
					deal[j].type_of_payment = (char *)malloc((strlen(customers[index].paymentDetails) + 1) * sizeof(char));
					strcpy(deal[j].type_of_payment, customers[index].paymentDetails);
					Write_to_deal_data();
					break;
				}
			}
			break;
		case 4:
			//update_filde = 4, user update the phone Number
			printf("Phone Number: ");
			scanf("%ld", &customers[index].phoneNumber);
			break;
		case 5:
			//update_filde = 5, user update the client status
			if (customers[index].Status != NULL) {
				free(customers[index].Status);
			}
			printf("what the new status: 1. NEW\n 2. LOST\n");
			getchar();
			gets(status);
			customers[index].Status = (char *)malloc((strlen(status) + 1) * sizeof(char));
			if (customers[index].Status == NULL) {
				printf("\n"); // add the error message
				return 0;
			}
			strcpy(customers[index].Status, status);
			if (strcmp(customers[index].Status, "LOST") == 0) {
				if (strcmp(customers[index].dealStatus, "WON") == 0 || strcmp(customers[index].dealStatus, "PENDING") == 0) {
					free(customers[index].dealStatus);
					customers[index].dealStatus = (char *)malloc((strlen("LOST") + 1) * sizeof(char));
					strcpy(customers[index].dealStatus, "LOST");
					if (strcmp(customers[index].dealStatus, "PENDING") == 0) {
						for (j = 0; j < number_oppurtunity; ++j) {
							if (opp[j].deal_number == customers[index].deal_id[customers[index].number_of_deal - 1]) {
								opp[j].client_id = 0;
								opp[j].worth = 0;
								free(opp[j].status);
								opp[j].status = NULL;
								opp[j].confidence = 0;
								free(opp[j].seler_name);
								opp[j].seler_name = NULL;
								opp[j].last_communication.day = 0;
								opp[j].last_communication.month = 0;
								opp[j].last_communication.year = 0;
								free(opp[j].product);
								opp[j].product = NULL;
								opp[j].number_of_products = 0;
								opp[j].deal_number = 0;
								break;
							}
						}
					}
					for (j = 0; j < number_deal; ++j) {
						if (deal[j].deal_number == customers[index].deal_id[customers[index].number_of_deal - 1]) {
							free(deal[j].status);
							deal[j].status = (char *)malloc((strlen(status) + 1) * sizeof(char));
							strcpy(deal[j].status, customers[index].dealStatus);
							Write_to_deal_data();
							break;
						}
					}
				}
			}
			else {
				add_new_deal(index);
			}
			customers[index].start.day = aTm->tm_mday;
			customers[index].start.month = aTm->tm_mon + 1;
			customers[index].start.year = aTm->tm_year + 1900;
			break;
		case 6:
			//update_filde = 6, user update the client's deal
			Uptade_deal(customers[index].deal_id[customers[index].number_of_deal - 1], index);
		default:
			flag = 1;
			break;
		}
	}
	Write_to_Client_Data();
//	reset();
}

void Uptade_deal(long int deal_id, long int customer_index) {
	long int i, index;
	int update_filde;
	int dealStatus_choice = -1;
	for (i = 0; i < number_deal; ++i) {
		if (deal[i].deal_number == deal_id) {
			index = i;
			break;
		}
	}
	time_t t = time(NULL);
	struct tm* aTm = localtime(&t);
	int flag = 0;
	while (flag == 0) {
//		yellow();
		printf("What information do you want to update?\n");
		printf("1. deal status\n 2. number of products\n 3.type of products\n  4.new deal 0. Save\n ");
		scanf("%d", &update_filde);
		switch (update_filde)
		{
		case 1:
			// uptade deal status
			if (strcmp(deal[index].status, "PENDING") == 0) {
//				boldgreen();
				printf("\n\n1.    Successful deal\n2.    Unsuccessful deal\n\nEneter final status of the deal: ");
				scanf("%d", &dealStatus_choice);
				while ((dealStatus_choice < 1 || dealStatus_choice > 3) && (dealStatus_choice <= '/' || dealStatus_choice >= ':')) {
//					red();
					printf("Wrong choice - Enter Your Choice: ");
					fseek(stdin, 0, SEEK_END);
//					boldgreen();
					scanf("%d", &dealStatus_choice);
				}
				if (deal[index].status != NULL) {
					free(deal[index].status);
				}
				if (dealStatus_choice == 1) {
					deal[index].status = (char*)malloc(strlen("WON") + 1 * sizeof(char));
					strcpy(deal[index].status, "WON");
				}
				else if (dealStatus_choice == 2) {
					deal[index].status = (char*)malloc(strlen("LOST") + 1 * sizeof(char));
					strcpy(deal[index].status, "LOST");
				}
				for (i = 0; i < number_oppurtunity; ++i) {
					if (opp[i].deal_number == deal[index].deal_number) {
						opp[i].client_id = 0;
						opp[i].worth = 0;
						free(opp[i].status);
						opp[i].status = NULL;
						opp[i].confidence = 0;
						free(opp[i].seler_name);
						opp[i].seler_name = NULL;
						opp[i].last_communication.day = 0;
						opp[i].last_communication.month = 0;
						opp[i].last_communication.year = 0;
						free(opp[i].product);
						opp[i].product = NULL;
						opp[i].number_of_products = 0;
						opp[i].deal_number = 0;
						break;
					}
				}
				deal[index].close.day = aTm->tm_mday;
				deal[index].close.month = aTm->tm_mon + 1;
				deal[index].close.year = aTm->tm_year + 1900;
				write_oppurtunity_data();
			}
			if (strcmp(deal[index].status, "LOST") == 0) {
				printf("1.    Successful deal\n2.    Pending deal\n\nEneter final status of the deal: ");
				scanf("%d", &dealStatus_choice);
				while ((dealStatus_choice < 1 || dealStatus_choice > 3) && (dealStatus_choice <= '/' || dealStatus_choice >= ':')) {
//					red();
					printf("Wrong choice - Enter Your Choice: ");
					fseek(stdin, 0, SEEK_END);
//					boldgreen();
					scanf("%d", &dealStatus_choice);
				}
				if (deal[index].status != NULL) {
					free(deal[index].status);
				}
				if (dealStatus_choice == 1) {
					deal[index].status = (char*)malloc(strlen("WON") + 1 * sizeof(char));
					strcpy(deal[index].status, "WON");
					deal[index].close.day = aTm->tm_mday;
					deal[index].close.month = aTm->tm_mon + 1;
					deal[index].close.year = aTm->tm_year + 1900;
				}
				else if (dealStatus_choice == 2) {
					deal[index].status = (char*)malloc(strlen("PENDING") + 1 * sizeof(char));
					strcpy(deal[index].status, "PENDING");
					deal[index].close.day = 0;
					deal[index].close.month = 0;
					deal[index].close.year = 0;
					add_oppurtunity(index, customer_index);
				}
			}
			if (customers[customer_index].Status != NULL) {
				free(customers[customer_index].dealStatus);
			}
			customers[customer_index].dealStatus = (char*)malloc((strlen(deal[index].status) + 1) * sizeof(char));
			strcpy(customers[customer_index].dealStatus, deal[index].status);
			break;
		case 2:
			deal[index].number_of_products = number_of_product();
			if (strcmp(deal[index].status, "PENDING") == 0) {
				for (i = 0; i < number_oppurtunity; ++i) {
					if (deal[index].deal_number == opp[i].deal_number) {
						opp[i].number_of_products = deal[index].number_of_products;
						break;
					}
				}
				write_oppurtunity_data();
			}
			break;
		case 3:
			if (deal[index].type_of_products != NULL) {
				free(deal[index].type_of_products);
			}
			choose_type_of_products(index);
			deal[index].total_price = calculate_price(index);
			if (strcmp(deal[index].status, "PENDING") == 0) {
				for (i = 0; i < number_oppurtunity; ++i) {
					if (deal[index].deal_number == opp[i].deal_number) {
						if (opp[i].product != NULL) {
							free(opp[i].product);
						}
						opp[i].product = (char*)malloc((strlen(deal[index].type_of_products) + 1) * sizeof(char));
						strcpy(opp[i].product, deal[index].type_of_products);
						opp[i].worth = deal[index].total_price;
						break;
					}
				}
				write_oppurtunity_data();
			}
			break;
		case 4:
			customers[customer_index].number_of_deal += 1;
			add_new_deal(customer_index);
		default:
			flag = 1;
			break;
		}
	}
	if (update_filde != 4) {
		deal[index].edit.day = aTm->tm_mday;
		deal[index].edit.month = aTm->tm_mon + 1;
		deal[index].edit.year = aTm->tm_year + 1900;
	}
	Write_to_deal_data();
}

void Write_to_deal_data() {
	int i;
	FILE *fp = fopen("deal_table.csv", "w");
	if (fp == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
		return fp;
	}
	for (i = 0; i < number_deal; ++i) {
		fprintf(fp, "%ld,%d,%s,%s,%d,%s,%s,%d,%d,%d,%d,%d,%d\n", deal[i].deal_number, deal[i].number_of_products, deal[i].type_of_products,
			deal[i].type_of_deal, deal[i].total_price, deal[i].status, deal[i].type_of_payment, deal[i].close.day, deal[i].close.month, deal[i].close.year, deal[i].edit.day, deal[i].edit.month, deal[i].edit.year);
	}
	Close_client_Data(fp);
}

void write_oppurtunity_data() {
	int i;
	FILE *fp = fopen("opportunity_table.csv", "w");
	if (fp == NULL) {
//		red();
		printf("\nFailed to open this File!\n\n");
		return fp;
	}
	for (i = 0; i < number_oppurtunity; ++i) {
		if (opp[i].status != NULL) {
			fprintf(fp, "%ld,%f,%s,%f,%s,%d,%d,%d,%s,%d,%ld\n", opp[i].client_id, opp[i].worth, opp[i].status, opp[i].confidence, opp[i].seler_name,
				opp[i].last_communication.day, opp[i].last_communication.month,
				opp[i].last_communication.year, opp[i].product, opp[i].number_of_products, opp[i].deal_number);
		}
	}
	Close_client_Data(fp);
}

//void boldgreen() {
//	printf("\033[1;32m");
//}

//void red() {
//	printf("\033[1;31m");
//}

//void cyan() {
//	printf("\033[1;36m");
//}

//void yellow() {
//	printf("\033[1;33m");
//}

//void reset() {
//	printf("\033[0m");
//}

void Free_client() {
	int i; 
	for (i = 0; i < number_client; ++i) {
		free(customers[i].firstName);
		free(customers[i].lastName);
		free(customers[i].emailAdress);
		free(customers[i].city);
		free(customers[i].livingAdress);
		free(customers[i].deal_id);
		free(customers[i].dealStatus);
		free(customers[i].paymentDetails);
		free(customers[i].Status);
	}
	free(customers);
}

void Free_deal() {
	int i;
	for (i = 0; i < number_deal; ++i) {
		free(deal[i].type_of_products);
		free(deal[i].type_of_deal);
		free(deal[i].status);
		free(deal[i].type_of_payment);
	}
	free(deal);
}

void Free_oppurtunity() {
	int i;
	for (i = 0; i < number_oppurtunity; ++i) {
		free(opp[i].status);
		free(opp[i].seler_name);
		free(opp[i].product);
	}
	free(opp);
}