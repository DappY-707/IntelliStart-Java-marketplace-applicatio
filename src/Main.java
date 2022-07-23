import  java.util.ArrayList;

public class Main {
    static ArrayList <User> users = new ArrayList<>();
    static ArrayList <Product> products = new ArrayList<>();

    static ArrayList <Purchase> purchases = new ArrayList<>();

    public static void main(String[] args) {
        initData();

        getUserList();
        try {
            purchase(2, 3);
            purchase(2, 2);
            purchase(1, 3);
        } catch (AmountException e) {
            System.out.println(e.getMessage());
        }
        listOfUserProducts(1);
        listOfProductsOwnedByUser(3);
        getUserList();
        getProductList();
    }

    /**
     * Метод виконує пошук продукту по його айді
     * @param id айді продукту який необхідно знайти
     * @return повертає обєкт Product
     */

    public static Product getProductById(int id) {  // пошук продукту по id
        Product currentProduct = null;
        for (Product product : products) {
            if (product.getId() == id) {
                currentProduct = product;
                break;
            }
        }
        return currentProduct;
    }

    /**
     * Метод виконує пошук користувача за його айді
     * @param id айді користувача який необхідно знайти
     * @return повертає обєкт User
     */

    public static User getUserById(int id) {   // пошук користувача по id
        User currentUser = null;
        for (User user : users) {
            if (user.getId() == id) {
                currentUser = user;
                break;
            }
        }
        return currentUser;
    }

    /**
     * Метод відображає перелік користувачів
     */

    public static void getUserList(){
        System.out.println("Перелік користувачів:");
        for (User user : users) {
            System.out.printf("id: %d, %s %s, поточний баланс: %f\n",user.getId(),user.getFirstName(),user.getLastName(),user.getAmountOfMoney());
        }
    }

    /**
     * Метод відображає перелік продуктів
     */

    public static void getProductList(){
        System.out.println("Список товару:");
        for (Product product  : products) {
            System.out.printf("%d) %s, ціна %f\n",product.getId(),product.getName(),product.getPrice());
        }
    }


    /**
     * Метод здійснює покупку користувачем товару
     * @param userId айді користувача, який хоче купити продукт
     * @param productId айді продукту, який користувач хоче купити
     * @throws AmountException викликає exception, якщо у користувача недостатньо грошей для покупки продукту
     */

    public static void purchase(int userId, int productId) throws AmountException {
        double userCash = getUserById(userId).getAmountOfMoney();
        double productCost = getProductById(productId).getPrice();
        double newAmount;
        if (userCash >= productCost) {
            System.out.printf("Покупець %s %s успішно придбав товар %s\n", getUserById(userId).getFirstName(), getUserById(userId).getLastName(), getProductById(productId).getName());
            newAmount = userCash - productCost;
            getUserById(userId).setAmountOfMoney(newAmount);
            purchases.add(new Purchase(userId, productId));
        } else {
            throw new AmountException("Помилка, не вистачає коштів для здійснення покупки");
        }

    }

    /**
     * Метод відопражає перелік продуктів користувача за його айлі
     * Якщо користувач ще нічого не купив, метод нічого не показує
     * @param id айді користувача, список продуктів якого необхідно переглянути
     */

    public  static  void listOfUserProducts (int id) {
        String userProductList = "";
        int productsCounter = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getUserId() == id) {
                productsCounter += 1;
                userProductList = userProductList + "\n" +getProductById(purchase.getProductId()).getName();
            }
        }
        if (productsCounter != 0) {
            System.out.printf("Користувач %s %s  придбав: %s \n", getUserById(id).getFirstName(), getUserById(id).getLastName(),userProductList);
        }
    }

    /**
     * Метод відображає список користувачів, що купили товар, по його айді
     * Якщо ніхто ще не купив цей товар, метод нічого не показує
     * @param id айді товару, список покупців якого необхідно переглянути
     */

    public  static  void listOfProductsOwnedByUser (int id) {
        String userList = "";
        int usersCounter = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getProductId() == id) {
                usersCounter += 1;
                userList = userList + "\n" +getUserById(purchase.getUserId()).getFirstName() + " " + getUserById(purchase.getUserId()).getLastName();
            }
        }
        if (usersCounter != 0) {
            System.out.printf("Продукт %s придбав(ли) %s \n", getProductById(id).getName(), userList);
        }
    }

    /**
     * Метод ініціалізує тестові данні для користувачів та продуктів
     */
    public static void initData() {
        // users
        users.add(new User(1, "Howard", "Rogers", 50));
        users.add(new User(2, "George", "Murphy", 75));
        users.add(new User(3, "Edgar", "Evans", 60));

        // products
        products.add(new Product(1, "Juice", 32));
        products.add(new Product(2, "Coffee", 40));
        products.add(new Product(3, "Water", 15));

    }


}
