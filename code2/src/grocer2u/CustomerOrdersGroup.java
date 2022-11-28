package grocer2u;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 要一起出車的 deliveryTrip 放一起
public class CustomerOrdersGroup
{
    private final int _deliveryTripID; // 索引
    private final List<CustomerOrders> _bufCustomerOrders = new ArrayList<>();

    //
    public CustomerOrdersGroup( int deliveryTripID )
    {
        _deliveryTripID = deliveryTripID ;
    }

    // 加入
    public void addCustomerOrders( CustomerOrders customerOrders )
    {
        _bufCustomerOrders.add( customerOrders );
    }

    // 取得列表
    public Iterator<CustomerOrders> getIterator()
    {
        return _bufCustomerOrders.iterator();
    }

    // 取得 id
    public int getDeliveryTripID()
    {
        return _deliveryTripID ;
    }
    // 取文字
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append( "Delivery trip " + _deliveryTripID + ":\n" );
        for( CustomerOrders customerOrders : _bufCustomerOrders )
            buf.append( "  " + customerOrders.toString() + "\n" );
        buf.append( "\n" );
        return buf.toString();
    }
}
