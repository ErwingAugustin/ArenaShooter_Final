// ENHANCEMENT: Added SQL Constraints and Security Checks
@Override
public void onCreate(SQLiteDatabase db) {
    // Added UNIQUE and NOT NULL to prevent duplicate accounts (Security/Integrity)
    String createUserTable = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT UNIQUE NOT NULL, " +
            "password TEXT NOT NULL)"; 
    db.execSQL(createUserTable);

    // Added CHECK constraints to prevent negative inventory (Data Integrity)
    String createInventoryTable = "CREATE TABLE inventory_items (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "item_name TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL CHECK (quantity >= 0), " +
            "low_stock_threshold INTEGER NOT NULL DEFAULT 5 CHECK (low_stock_threshold >= 0))";
    db.execSQL(createInventoryTable);
}

// ENHANCEMENT: Added Business Logic for Low Stock Alerts
public Cursor getLowStockItems() {
    SQLiteDatabase db = this.getReadableDatabase();
    // This query finds items where stock is less than or equal to the threshold
    return db.rawQuery("SELECT * FROM inventory_items WHERE quantity <= low_stock_threshold", null);
}