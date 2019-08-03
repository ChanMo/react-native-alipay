//
//  CAlipayManager.h
//
//  Created by chen on 2017/12/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#ifndef CAlipayManager_h
#define CAlipayManager_h

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <AlipaySDK/AlipaySDK.h>

@interface CAlipayManager : RCTEventEmitter <RCTBridgeModule>
- (void) onResp:(NSDictionary *)resp;
@end

#endif /* CAlipayManager_h */
