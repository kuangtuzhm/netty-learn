package com.zealot.netty.learn.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
 

public class GeneralUtils {
    private static final Logger log=Logger.getLogger(GeneralUtils.class);
    
    
    public static final int UNSIGNED_INT32_MAX_VALUE=0xffffffff;
    
    
	public static String get16Str(byte[] bytes){
	    if(null != bytes&& bytes.length>0){
		StringBuilder sbu=new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
				sbu.append(hex.toUpperCase()+ " ");
			} 
			return sbu.toString(); 
	    }
	    return "";
	}
	
	
    /**
     * 字符串
     */
    private  static StringBuffer strBuf=new StringBuffer() ;
    
    static {
        for (int i = 'a'; i <= 'z'; i++) {
            strBuf.append((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            strBuf.append((char) i);
        }
        for (int i = '0'; i <= '9'; i++) {
            strBuf.append((char) i);
        }
    }
    /**
     *生成UUID字符串 
     */
      public static String getUUID(){
          return UUID.randomUUID().toString().replaceAll("-", "");
      }
    /**
     * 最快获了随机字符串
     * @param len
     * @return
     */
    public static final String getRandomString(int len){
        StringBuffer sb=new StringBuffer();
        Random r=new Random();
        while(sb.length()<len){
            int start=r.nextInt(26);
            sb.append(strBuf.substring(start));
        }
        sb.setLength(len);
        return sb.toString();
    }
    public static final String protocolDateFmt="yy:MM:dd:HH:mm:ss";
    
    /**
     * 是否合法IMEI
     * @param imei
     * @return
     */
    public static boolean isImei(String imei){
        if(StringUtils.isNotBlank(imei)&&(imei.matches("[0-9]{15}")||imei.matches("[0-9]{16}"))){
            return true;
        }
        return false;
    }
    /**
     * IMEI解码
     * @param imei
     * @return
     */
    public static String decodeImei(byte[] imei) {
        StringBuilder sbu = new StringBuilder(15);
        for (int i = 0; i < imei.length; i++) {
            String hex = Integer.toHexString(imei[i] & 0xFF);
            if (hex.length() == 1 && i != 0) {
                hex = '0' + hex;
            }
            sbu.append(hex);
        }
        return sbu.toString();
    }
    /**
     * IMEI编码
     * @param imei
     * @return
     */
    public static byte[] encodeImei(String imei){
        byte[] imeiData=null;
        if(isImei(imei)){
            imeiData=new byte[8];
            imei="0"+imei;
            for (int i = 0; i < 8; i++) {
                int start=i*2;
                String hex=imei.substring(start,start+2);
                imeiData[i]=UnsignedBytes.parseUnsignedByte(hex, 16);
            }
            return imeiData;
        }
        return null;
    }
    /**
     * Hex字符串转成byte数组
     * @param data
     * @param split
     * @return
     */
    public static byte[] hexStringToByteArray(String data,String split){
        byte[] hexData=null;
        if(StringUtils.isNotBlank(data)&&StringUtils.isNotEmpty(split)){
             List<String> hexList = Splitter.on(split).trimResults().omitEmptyStrings().splitToList(data);
             hexData=new byte[hexList.size()] ;
             for (int i = 0; i < hexData.length; i++) {
                byte val= UnsignedBytes.parseUnsignedByte(hexList.get(i), 16);
                hexData[i]=val;
            }
        }
       return hexData;
    }
    /**
     * 时间编码
     * @param date
     * @return
     */
    public static byte[] encodeDate(Date date){
        byte[] data=null;
        if(null!=date){
            SimpleDateFormat sdf=new SimpleDateFormat(protocolDateFmt);
            String dateStr=sdf.format(date);
            List<String> dateStrList = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(dateStr);
            data=new byte[6];
            for (int i = 0; i < dateStrList.size(); i++) {
                data[i]=UnsignedBytes.parseUnsignedByte(dateStrList.get(i), 10);
            }
        }
        return data;
    }
    /**
     * 时间解码
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date decodeDate(byte[] data) {
       
        if(null!=data&&data.length==6){
            StringBuffer   sb=new StringBuffer();
           for (int i = 0; i < data.length; i++) {
               if(i>0){
                   sb.append(":");
               }
               sb.append(data[i]);
           }
           SimpleDateFormat sdf=new SimpleDateFormat(protocolDateFmt);
           try {
               return sdf.parse(sb.toString());
            } catch (ParseException e) {
             return null;
            }
        }
        
        return null;
    }

	/**
	 * 解码经伟 度
	 * 
	 * @param data
	 * @return
	 */
	public static double decodeLatLng(byte[] data) {
		ByteBuf proData = Unpooled.wrappedBuffer(data);
		if (proData.readableBytes() == 4) {

			Integer temp = proData.readInt();
			proData.release();
			Double temp1 = (double) (new Double(temp) / new Double(1800000));
			BigDecimal toResult = new BigDecimal(temp1);
			return toResult.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

		} else {
			log.error("decodeLatLng Bytes length error ,legnth:= "
					+ proData.readableBytes());
			return 0;

		}
		// Integer temp=Integer.parseInt(ByteUtil.bytesToHexString(data), 16);
	}
    /**
     * 编码经伟 度
     * @param data
     * @return
     */
    public static byte[] encodeLatLng(double val){
        Double  result=val*1800000;
        int intVal=result.intValue();
        return Ints.toByteArray(intVal);
    }
    /**
     * 解码 时间，Byte to Date
     * @param data
     * @return
     */
    public static Date decodeTimeFromByte(byte[] posTimeByte){
 
   	 Calendar cal=Calendar.getInstance(); 
   	// int zoneOffset = cal.get(Calendar.ZONE_OFFSET);  

   		if(posTimeByte!=null&&posTimeByte.length==6){  
   			if(posTimeByte[0]==0&&posTimeByte[1]==0&&posTimeByte[2]==0&&posTimeByte[3]==0&&posTimeByte[4]==0&&posTimeByte[5]==0)
   			{                                 
   				log.error("gps time is null ,0 ");
   				return null;     
   			}
   			cal.set(2000+posTimeByte[0], posTimeByte[1]-1, posTimeByte[2], posTimeByte[3], posTimeByte[4], posTimeByte[5]);
   	//		cal.add(Calendar.MILLISECOND, zoneOffset);
   			    
   			return cal.getTime(); 
   		}else
   			return null;
      
    }
    
    
    /**
     * 解码 时间 针对 GT06机型，将北京时间转成UTC
     * @param data
     * @return
     */
    public static Date decodeTimeFromByte2UTC(byte[] posTimeByte){
   
   	 Calendar cal=Calendar.getInstance(); 
   	 int zoneOffset = 28800000;   

   		if(posTimeByte!=null&&posTimeByte.length==6){  
   			if(posTimeByte[0]==0&&posTimeByte[1]==0&&posTimeByte[2]==0&&posTimeByte[3]==0&&posTimeByte[4]==0&&posTimeByte[5]==0)
   			{                                 
   				log.error("gps time is null ,0 ");
   				return null;     
   			}                  
   			cal.set(2000+posTimeByte[0], posTimeByte[1]-1, posTimeByte[2], posTimeByte[3], posTimeByte[4], posTimeByte[5]);
   			cal.add(Calendar.MILLISECOND, -zoneOffset);
   			       
   			return cal.getTime(); 
   		}else
   			return null;
        
    }
    
    /**
     * 解码 时间，Byte to String 
     * @param data
     * @return
     */
    public static String  decodeTimeStrFromByte(byte[] posTimeByte){
 
    	if(posTimeByte!=null&&posTimeByte.length==6)
            return  posTimeByte[0]+"年"+posTimeByte[1]+"月"+posTimeByte[2]+"日"+posTimeByte[3]+"时"+posTimeByte[4]+"分"+posTimeByte[5]+"秒";
    	else
		return null;
 
      
    }
    
 
	

	
    /**
     * 8个Bit位转成byte
     * @param data
     * @return
     */
    public static Byte BitToByte(String bit7,String bit6,String bit5,String bit4,String bit3,String bit2,String bit1,String bit0){
    	String temp=bit7+bit6+bit5+bit4+bit3+bit2+bit1+bit0;
    	if(temp.length()==8){
    	 return 	Integer.valueOf(temp,2).byteValue();
    	}
		return null;
     
    }

    /**
     * 
     * @param data
     * @param offset
     * @param limit
     * @author chengxuwei
     */
    public static String getHexString(byte[] data,int offset,int limit){
        if(null!=data&&data.length>=(offset+limit)){
            StringBuffer sb=new StringBuffer();
            for (int i = offset; i < offset+limit; i++) {
                sb.append(String.format("%02x", data[i]));
                sb.append(", ");
            }
           return sb.toString();
        }
        return "";
    }

    /**
     * 
     * @param data
     * @author chengxuwei
     */
    public static String getHexString(byte[] data){
	if(null ==data){
	    return "";
	}
        return getHexString(data, 0, data.length);
    }


//    /**
//     * 设位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static short setBit(Short src,int pos){
//     
//      if(pos>-1&&pos<16){
//         return (short) (src|( (1<<pos)&0xffff));
//      }
//      return src;
//    }
//    /**
//     * 设位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static byte setBit(byte src,int pos){
//     
//      if(pos>-1&&pos<8){
//         return (byte) (src|( (1<<pos)&0xffff));
//      }
//      return src;
//    }
//    /**
//     * 清除某一位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static short clearBit(Short src,int pos){
//      if(1==getBit(src, pos)){
//        if(pos>-1&&pos<16){
//          src=(short) (src^ (1<<pos));
//        }
//      }
//      return src;
//    }
//    /**
//     * 清除某一位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static byte clearBit(byte src,int pos){
//      if(1==getBit(src, pos)){
//        if(pos>-1&&pos<8){
//          src=(byte) (src^ (1<<pos));
//        }
//      }
//      return src;
//    }
//    /**
//     * 取位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static int getBit(Short src,int pos){
//      return (src>>pos)&0x01;
//    }
//    /**
//     * 取位
//     * @param src
//     * @param pos
//     * @return
//     * @author chengxuwei
//     */
//    public static int getBit(byte src,int pos){
//      return (src>>pos)&0x01;
//    }
    /**
     * 设位
     * @param src
     * @param pos
     * @return
     * @author chengxuwei
     */
    public static int setBit(int src,int pos){
        int val=1<<pos;
        return  src|val;
    }
    /**
     * 清除某一位
     * @param src
     * @param pos
     * @return
     * @author chengxuwei
     */
    public static int clearBit(int src,int pos){
      if(1==getBit(src, pos)){
         src=(short) (src^ (1<<pos));
      }
      return src;
    }
    /**
     * 取位
     * @param src
     * @param pos
     * @return
     * @author chengxuwei
     */
    public static int getBit(int src,int pos){
      return (src>>pos)&0x01;
    }
    /**
     * 测试某位的值
     * @param n
     * @param pos 最低位为0，取值(0~31)
     * @return
     * @author chengxuwei
     */
    public static int testBit(int n,int pos){
        if(pos>0&&pos<=32){
            n>>=pos;
            return n&=0x01;
        }
        return 0;
    }
    /**
     * JAVA 生成的UNICODE会带有头，FE FF
 
     * @return
     * @author xiehongtao
     */
    public static byte[] delUnicodeHead(byte arrays[]){
      return     Arrays.copyOfRange(arrays, 2, arrays.length);
    }
    
    
    

	public static String getIMIE(byte[] imie){
		StringBuilder sbu=new StringBuilder(15);
		for (int i = 0; i < imie.length; i++) {
			String hex = Integer.toHexString(imie[i] & 0xFF); 
			if (hex.length() == 1 && i!=0) { 
				hex = '0' + hex; 
			} 
				sbu.append(hex.toUpperCase());
			} 
			return sbu.toString(); 
	}
 
    /**
     * 序号生成器
     * @return String
     * @author xiehongtao
     */

	public static int genSerialNO(){ 
		Long time=System.currentTimeMillis();
		String timeStr=(String) time.toString().subSequence(3, time.toString().length());
	    return Integer.parseInt(timeStr);
	}

	
	
	
	public static void main(String[] args) {
		
//		byte[] esf=	encodeLatLng(-27.862307);
//		 System.out.println( getHexString(esf) );
//		 
		
		
	 byte[] la={(byte) 0xfd, 0x02, (byte) 0xbd ,0x28};
	//	byte[] la={	0x10, 0x73 ,(byte) 0xe6 ,80};
	
 
	 System.out.println( decodeLatLng(la)) ;
 
//	
//		byte ffes=Integer.valueOf("11111111",2).byteValue();
//		byte www = (byte) 0xFF;  
//		byte ssss=(byte) (ffes&0xFF);
//		
//	    double lng=113.123456;
//	    byte[] lngBuf=encodeLatLng(lng);
//	    double lng1=decodeLatLng(lngBuf);
//        System.out.println(lng1);
//      
//        System.out.println(  BitToByte("1","1","1","1","1","1","1","1") );
	    

//	    System.out.println(getBits(0xcc, 0, 4));
//	  short s=0;
//
//	  s=setBit(s, 14);
//	 short n= (short) (( s>>8));
//     int c11=(s>>8)&0xff;
//     int c10=s&0xff;
//	 System.out.println(Integer.toHexString(c11));
//	 System.out.println(Integer.toHexString(c10));
//	  for (int i = 15; i >=0; i--) {
//	    System.out.print(getBit(n,i)+",");
//        
//      }
	 
    }
    
 
}
