package grocer2u;

class CustomerOrders
{
    static final String [] PriorityLevel = { "Urgent" , "Prioritised" , "Standard" };

    // cost
    int _cost ; // 最小成本
    int _PriorityLevel = -1 ;// 優先等級
    int _DeliveryTrip = -1 ; // 送貨行程

    final String _src ; // 來源
    final int Order_of_Input; // 輸入順序，
    final String 		Priority;// 優先，
    final String Customer_Name;// 顧客姓名，
    final int 		Crates_in_Order; //重量，
    final String Location; // 地點

    CustomerOrders( String src , String [] data )
    {
        int i ;

        _src = src ;
        Order_of_Input = Integer.parseInt(  data[0] );
        Priority = data[1] ;
        Customer_Name = data[2] ;
        Crates_in_Order =Integer.parseInt(  data[3] );
        Location = data[4] ;
        // 初始優先等級
        for( i = 0 ; i < PriorityLevel.length ; ++i )
            if( PriorityLevel[i].equals( Priority ))
        {
            _PriorityLevel = i ;
            break ;
        }
        if( _PriorityLevel < 0 )
            System.out.println( "this data not PriorityLevel: " + Priority );
    }

    // 初始化函式
    public void initNode()
    {
        _cost = -1 ;
    }
    // 比較優先等級
    public int compareTo( CustomerOrders arg1 )
    {
        if( this._PriorityLevel != arg1._PriorityLevel )
            return this._PriorityLevel - arg1._PriorityLevel ;
        return this.Order_of_Input - arg1.Order_of_Input ;
    }
    //  取得送貨行程
    public int getDeliveryTrip()
    {
        return _DeliveryTrip ;
    }
    // 設定送貨行程
    public void setDeliveryTrip( int DeliveryTrip )
    {
        this._DeliveryTrip = DeliveryTrip ;
    }

    // 輸出字串
    @Override
    public String toString()
    {
        return String.format( "%s, %d crates, %s" , Location , Crates_in_Order , Customer_Name );
    }
};