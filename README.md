# add-to-cart
Simple Add to Cart UI with demo functionality in Native Android Java

Workflow:
A user can click on any of the presented products on homescreen to view it's details. On product's detail page, the product can be added to the cart. Later on from homescreen, a user may choose to go to the cart and increase or decrease the count of any added product. A user can also see the amount due on cart page.

Functionality:
In MainActivity, RecyclerView is used to list down the demo products. Further, onClick event is embedded on RecyclerView itself to go to the ProductDetailActivity.
Another RecyclerView is implemented to show the CartList.
