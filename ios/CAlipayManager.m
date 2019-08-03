//
//  CAlipayManager.m
//
//  Created by chen on 2017/12/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CAlipayManager.h"
#import <React/RCTLog.h>

@implementation CAlipayManager

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_REMAP_METHOD(pay,
                 orderString:(NSString *)orderString
                 payWithResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
  NSString *appScheme = @"alipay2018072860772644";
  [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
    NSLog(@"reslut = %@",resultDic);
    if (resultDic) {
      NSLog(@"alipay callback");
      resolve(resultDic);
    } else {
      NSError* error = nil;
      reject(@"pay error", @"pay error", error);
    }
  }];
}

+ (id)allocWithZone:(struct _NSZone *)zone
{
  static CAlipayManager *alipay = nil;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    alipay = [super allocWithZone:zone];
  });
  return alipay;
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"alipayResult"];
}

- (void) onResp:(NSDictionary *)resp {
  NSLog(@"alipay reslut = %@", resp);
  [self sendEventWithName:@"alipayResult" body:resp];
}
@end
